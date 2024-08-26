package org.example;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Phone> phones;

    public Inventory() {
        phones = new ArrayList<>();
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
    }

    public void updatePhone(String modelName, Phone updatedPhone) {
        for (int i = 0; i < phones.size(); i++) {
            if (phones.get(i).getModelName().equalsIgnoreCase(modelName)) {
                phones.set(i, updatedPhone);
                return;
            }
        }
    }

    public void deletePhone(String modelName) {
        phones.removeIf(phone -> phone.getModelName().equalsIgnoreCase(modelName));
    }

    public Phone searchPhone(String modelName) {
        for (Phone phone : phones) {
            if (phone.getModelName().equalsIgnoreCase(modelName)) {
                return phone;
            }
        }
        return null;
    }

    public List<Phone> getAllPhones() {
        return phones;
    }
}
