import java.util.ArrayList;
import java.util.Scanner;

class Car {
    private String id;
    private String model;
    private String brand;
    private double basePrice;
    private boolean isAvailable;

    public Car(String id, String model, String brand, double basePrice) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.basePrice = basePrice;
        this.isAvailable = true;
    }

    // Getter methods
    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public boolean getisAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

    public double calculatePrice(int days) {
        return days * basePrice;
    }

    public String getId() {
        return id;
    }
}

class Customer {
    private String name;
    private int id;

    public Customer(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class RentalSystem {
    private ArrayList<Car> cars;
    private ArrayList<Customer> customers;
    private ArrayList<Rental> rentals;

    public RentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    void addCar(Car car) {
        cars.add(car);
    }

    void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.getisAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
            System.out.println("Car rented successfully to " + customer.getName() + " for " + days + " days.");
        } else {
            System.out.println("Car is not available for rent. Try another car.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental toReturn = null;
        for (Rental it : rentals) {
            if (it.getCar() == car) {
                toReturn = it;
                break;
            }
        }
        if (toReturn != null) {
            rentals.remove(toReturn);
            System.out.println("Car is returned successfully.");
        } else {
            System.out.println("Car was not rented.");
        }
    }

    private Car findCarById(String id) {
        for (Car car : cars) {
            if (car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    private Customer findCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n---- Rental System Menu ----");
            System.out.println("1. Add Car");
            System.out.println("2. Add Customer");
            System.out.println("3. Rent a Car");
            System.out.println("4. Return a Car");
            System.out.println("5. View Available Cars");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Car ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Car Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter Car Brand: ");
                    String brand = scanner.nextLine();
                    System.out.print("Enter Base Price: ");
                    double basePrice = scanner.nextDouble();
                    Car newCar = new Car(id, model, brand, basePrice);
                    addCar(newCar);
                    System.out.println("Car added successfully.");
                    break;

                case 2:
                    System.out.print("Enter Customer Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Customer ID: ");
                    int customerId = scanner.nextInt();
                    Customer newCustomer = new Customer(name, customerId);
                    addCustomer(newCustomer);
                    System.out.println("Customer added successfully.");
                    break;

                case 3:
                    System.out.print("Enter Customer ID to rent a car: ");
                    int rentCustomerId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Car ID to rent: ");
                    String rentCarId = scanner.nextLine();
                    System.out.print("Enter number of days: ");
                    int days = scanner.nextInt();
                    Customer rentingCustomer = findCustomerById(rentCustomerId);
                    Car rentingCar = findCarById(rentCarId);
                    if (rentingCustomer != null && rentingCar != null) {
                        rentCar(rentingCar, rentingCustomer, days);
                    } else {
                        System.out.println("Invalid Customer ID or Car ID.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Car ID to return: ");
                    String returnCarId = scanner.nextLine();
                    Car returningCar = findCarById(returnCarId);
                    if (returningCar != null) {
                        returnCar(returningCar);
                    } else {
                        System.out.println("Car ID not found.");
                    }
                    break;

                case 5:
                    System.out.println("Available Cars:");
                    for (Car car : cars) {
                        if (car.getisAvailable()) {
                            System.out.println("ID: " + car.getId() + ", Model: " + car.getModel() + ", Brand: " + car.getBrand() + ", Price per Day: " + car.getBasePrice());
                        }
                    }
                    break;

                case 6:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        RentalSystem rental = new RentalSystem();
        rental.menu();
    }
}
