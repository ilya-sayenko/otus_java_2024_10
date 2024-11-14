package ru.otus.homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final Comparator<Customer> CUSTOMER_SCORES_COMPARATOR = Comparator.comparing(Customer::getScores);

    private final NavigableMap<Customer, String> customerData = new TreeMap<>(CUSTOMER_SCORES_COMPARATOR);

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> firstEntry = customerData.firstEntry();
        if (firstEntry == null) {
            return null;
        }

        return copyEntry(firstEntry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> higherEntry = customerData.higherEntry(customer);
        if (higherEntry == null) {
            return null;
        }

        return copyEntry(higherEntry);
    }

    public void add(Customer customer, String data) {
        customerData.put(customer, data);
    }

    private Map.Entry<Customer, String> copyEntry(Map.Entry<Customer, String> firstEntry) {
        Customer firstCustomer = firstEntry.getKey();

        return Map.entry(
                new Customer(firstCustomer.getId(), firstCustomer.getName(), firstCustomer.getScores()),
                firstEntry.getValue()
        );
    }
}
