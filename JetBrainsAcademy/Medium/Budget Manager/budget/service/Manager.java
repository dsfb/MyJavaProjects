package budget.service;

import budget.model.Purchase;
import budget.repository.PurchaseRepository;

import java.io.*;
import java.util.*;

public class Manager {
    private final Scanner sc = new Scanner(System.in);
    private final PurchaseRepository purchases = new PurchaseRepository();
    private final String dataFileName = "purchases.txt";
    private double income;
    private ArrayList<String> typeMenuList = new ArrayList<>() {
        {
            this.add("Food");
            this.add("Clothes");
            this.add("Entertainment");
            this.add("Other");
        }
    };
    private String[] sortMenu = new String[] {
            "Sort all purchases",
            "Sort by type",
            "Sort certain type"
    };

    public String getTypeMenu(int index) {
        return this.typeMenuList.get(index);
    }

    public int getPurchaseType() {
        System.out.println("Choose the type of purchase");
        for (int i = 0; i < typeMenuList.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, typeMenuList.get(i));
        }
        System.out.printf("%d) Back\n", typeMenuList.size() + 1);
        String option = sc.nextLine();
        return Integer.parseInt(option) - 1;
    }

    public int getSortOption() {
        System.out.println("\nHow do you want to sort?");
        for (int i = 0; i < this.sortMenu.length; i++) {
            System.out.printf("%d) %s\n", i + 1, sortMenu[i]);
        }
        System.out.printf("%d) Back\n", sortMenu.length + 1);
        String option = sc.nextLine();
        return Integer.parseInt(option);
    }

    public void addPurchase(String type) {
        System.out.println("\nEnter purchase name:");
        String purchaseName = sc.nextLine();
        System.out.println("Enter its price:");
        double purchaseValue = Double.parseDouble(sc.nextLine());
        Purchase purchase = new Purchase(purchaseName, purchaseValue);
        purchases.addPurchase(type, purchase);
        System.out.printf("Purchase was added!\n\n");
    }

    public void addIncome() {
        System.out.println("\nEnter income:");
        double newIncome = Double.parseDouble(sc.nextLine());
        income += newIncome;
        System.out.printf("Income was added!\n\n");
    }

    public void showPurchases() {
        if (purchases.getTotalPurchase() == 0) {
            System.out.println("\nThe purchase list is empty\n");
        } else {
            System.out.printf("\n");
            int option;
            do {
                System.out.println("Choose the type of purchases");
                for (int i = 0; i < typeMenuList.size(); i++) {
                    System.out.printf("%d) %s\n", i + 1, typeMenuList.get(i));
                }
                int allIndex = typeMenuList.size() + 1;
                System.out.printf("%d) All\n", allIndex);
                System.out.printf("%d) Back\n", allIndex + 1);
                option = Integer.parseInt(sc.nextLine());
                if (option > allIndex) {
                    System.out.println();
                    return;
                } else if (option == allIndex) {
                    System.out.println("\nAll:");
                } else {
                    System.out.printf("\n%s:\n", typeMenuList.get(option - 1));
                }
                double totalSum = 0;
                if (option < allIndex) {
                    for (Purchase purchase : this.purchases.getListOfPurchases(typeMenuList.get(option - 1))) {
                        System.out.println(purchase);
                        totalSum += purchase.getPrice();
                    }
                } else if (option == allIndex) {
                    for (List<Purchase> purchaseList : this.purchases.getPurchases()) {
                        for (Purchase purchase : purchaseList) {
                            System.out.println(purchase);
                        }
                    }
                    totalSum = this.purchases.getTotalPurchase();
                }
                System.out.printf("Total sum: $%.2f\n\n", totalSum);
            } while (option < typeMenuList.size() + 2);
        }
    }

    public void showBalance() {
        System.out.printf("\nBalance: $%.2f\n\n", Math.max(income - this.purchases.getTotalPurchase(), 0.0));
    }

    public void processSortingOption() {
        int option = 0;
        do {
            option = getSortOption();
            switch (option) {
                case 1:
                    this.sortAll();
                    break;
                case 2:
                    this.sortByType();
                    break;
                case 3:
                    this.sortCertainType();
                    break;
                default:
                    break;
            }
        } while (option < sortMenu.length + 1);
        System.out.println();
    }

    private Comparator purchaseComparator = new Comparator<Purchase>() {
        @Override
        public int compare(Purchase p1, Purchase p2) {
            return -Double.compare(p1.getPrice(), p2.getPrice());
        }
    };

    public void sortAll() {
        List<List<Purchase>> allPurchases = this.purchases.getPurchases();
        List<Purchase> purchaseList = new ArrayList<>();
        for (List<Purchase> purchaseSubList : allPurchases) {
            purchaseList.addAll(purchaseSubList);
        }
        if (purchaseList.isEmpty()) {
            System.out.printf("\nThe purchase list is empty!\n");
        } else {
            Collections.sort(purchaseList, purchaseComparator);
            System.out.println("\nAll:");
            for (Purchase purchase : purchaseList) {
                System.out.println(purchase);
            }
            System.out.printf("Total: $%.2f\n", this.purchases.getTotalPurchase());
        }
    }

    public void sortByType() {
        System.out.println("\nTypes:");
        if (this.purchases.getTotalPurchase() == 0d) {
            for (String type: typeMenuList) {
                System.out.printf("%s - $0\n", type);
            }
            System.out.println("Total sum: $0");
        } else {
            List<Purchase> typePurchases = new ArrayList<>();
            for (String type : typeMenuList) {
                double result = this.purchases.getListOfPurchases(type).stream().
                        mapToDouble(Purchase::getPrice).sum();
                typePurchases.add(new Purchase(type, result));
            }
            Collections.sort(typePurchases, purchaseComparator);
            for (Purchase purchase : typePurchases) {
                System.out.printf("%s - $%.2f\n", purchase.getName(), purchase.getPrice());
            }
            System.out.printf("Total sum: $%.2f\n", this.purchases.getTotalPurchase());
        }
    }

    public void sortCertainType() {
        System.out.println("\nChoose the type of purchase");
        for (int i = 0; i < typeMenuList.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, typeMenuList.get(i));
        }
        String option = sc.nextLine();
        int typeIndex = Integer.parseInt(option) - 1;

        String typeStr = typeMenuList.get(typeIndex);
        List<Purchase> purchaseList = this.purchases.getListOfPurchases(typeStr);
        if (purchaseList.isEmpty()) {
            System.out.println("\nThe purchase list is empty!");
        } else {
            Collections.sort(purchaseList, purchaseComparator);

            System.out.printf("\n%s:\n", typeStr);
            double total = 0;
            for (Purchase purchase : purchaseList) {
                System.out.println(purchase);
                total += purchase.getPrice();
            }
            System.out.printf("Total sum: $%.2f\n", total);
        }
    }

    public void saveData() {
        File file = new File(this.dataFileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Cannot create the file: " + file.getPath());
        }

        try (OutputStream fs = new FileOutputStream(file);
             PrintStream ps = new PrintStream(fs);) {
            ps.println(income);
            for (String type : this.purchases.getPurchaseTypes()) {
                ps.println(type);
                for (Purchase purchase : this.purchases.getListOfPurchases(type)) {
                    ps.printf("%s\t%.2f\n", purchase.getName(), purchase.getPrice());
                }
            }
            System.out.printf("\nPurchases were saved!\n\n");
        } catch (IOException e) {
            System.out.println("Error on saving data: " + e.getMessage());
        }
    }

    public void loadData() {
        File file = new File(this.dataFileName);
        String purchaseType = null;
        String purchaseName = null;
        double purchasePrice;
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            String data = br.readLine();
            this.income = Double.parseDouble(data);
            while (br.ready()) {
                data = br.readLine();
                if (this.typeMenuList.contains(data)) {
                    purchaseType = data;
                } else {
                    String[] tokens = data.split("\t");
                    purchaseName = tokens[0];
                    purchasePrice = Double.parseDouble(tokens[1]);
                    this.purchases.addPurchase(purchaseType, new Purchase(purchaseName, purchasePrice));
                }
            }

            System.out.printf("\nPurchases were loaded!\n\n");
        } catch (IOException e) {
            System.out.println("Error on loading data: " + e.getMessage());
        }
    }
}
