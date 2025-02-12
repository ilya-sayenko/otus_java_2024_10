package ru.otus.appcontainer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();

    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) {
        Arrays.stream(initialConfigClasses)
                .filter(clazz -> clazz.isAnnotationPresent(AppComponentsContainerConfig.class))
                .sorted(Comparator.comparingInt(clazz -> clazz.getAnnotation(AppComponentsContainerConfig.class).order()))
                .forEach(this::processConfig);
    }

    public AppComponentsContainerImpl(String packageName) {
        new Reflections(packageName).getTypesAnnotatedWith(AppComponentsContainerConfig.class)
                .stream()
                .sorted(Comparator.comparingInt(clazz -> clazz.getAnnotation(AppComponentsContainerConfig.class).order()))
                .forEach(this::processConfig);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            Object config = configClass.getConstructor().newInstance();
            List<Method> methods = Arrays.stream(configClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(AppComponent.class))
                    .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                    .toList();

            for (Method method : methods) {
                String componentName = method.getAnnotation(AppComponent.class).name();
                if (appComponentsByName.get(componentName) != null) {
                    throw new RuntimeException(String.format("Component %s has been already created", componentName));
                }

                List<Object> arguments = findMethodArguments(method);
                Object bean = arguments.isEmpty() ? method.invoke(config) : method.invoke(config, arguments.toArray());
                appComponents.add(bean);
                appComponentsByName.put(componentName, bean);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<Object> findMethodArguments(Method method) {
        List<Object> arguments = new ArrayList<>();
        for (Parameter parameter : method.getParameters()) {
           arguments.add(getAppComponent(parameter.getType()));
        }

        return arguments;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> components = appComponents.stream()
                .filter(component -> {
                    Class<?> clazz = component.getClass();
                    return clazz.equals(componentClass) || componentClass.isAssignableFrom(clazz);
                })
                .toList();

        if (components.isEmpty()) {
            throw new RuntimeException("Component not found");
        }

        if (components.size() > 1) {
            throw new RuntimeException("Too many duplicates");
        }

        return (C) components.getFirst();
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new RuntimeException("Component not found");
        }

        return (C) component;
    }
}
