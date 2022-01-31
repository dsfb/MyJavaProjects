package budget.repository;

import budget.model.Purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseRepository {
    private Map<String, List<Purchase>> purchaseMap = new HashMap<>();
    private double totalPurchase = 0;

    public void addPurchase(String type, Purchase purchase) {
        if (purchaseMap.containsKey(type)) {
            purchaseMap.get(type).add(purchase);
        } else {
            purchaseMap.put(type, new ArrayList<>());
            purchaseMap.get(type).add(purchase);
        }

        totalPurchase += purchase.getPrice();
    }

    public List<Purchase> getListOfPurchases(String type) {
        List<Purchase> result = this.purchaseMap.get(type);
        if (result == null) {
            return new ArrayList<>();
        }

        return result;
    }

    public double getTotalPurchase() {
        return this.totalPurchase;
    }

    public List<List<Purchase>> getPurchases() {
        return new ArrayList<>(this.purchaseMap.values());
    }

    public List<String> getPurchaseTypes() {
        return new ArrayList<>(this.purchaseMap.keySet());
    }
}
