package ru.otus.homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final Comparator<Customer> CUSTOMER_SCORES_COMPARATOR = Comparator.comparing(Customer::getScores);

    private final TreeMap<Customer, String> customerData = new TreeMap<>(CUSTOMER_SCORES_COMPARATOR);

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> firstEntry = customerData.firstEntry();
        if (firstEntry == null) {
            return null;
        }
        Customer firstCustomer = firstEntry.getKey();

        return Map.entry(
                new Customer(firstCustomer.getId(), firstCustomer.getName(), firstCustomer.getScores()),
                firstEntry.getValue()
        );
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> higherEntry = customerData.higherEntry(customer);
        if (higherEntry == null) {
            return null;
        }
        Customer higherCustomer = higherEntry.getKey();

        return Map.entry(
                new Customer(higherCustomer.getId(), higherCustomer.getName(), higherCustomer.getScores()),
                higherEntry.getValue()
        );
    }

    public void add(Customer customer, String data) {
        customerData.put(customer, data);
    }
}
