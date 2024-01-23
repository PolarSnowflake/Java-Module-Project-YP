import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.start();
    }
}
class Calculator {
    private Item[] items = new Item[100]; // Массив для хранения товаров
    private int numberOfPeople; // Количество людей
    private int itemCount = 0; // Счетчик товаров
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("Введите количество людей (больше 1):");
            int nop = getInputInt();

            if (nop > 1) {
                numberOfPeople = nop;
                break;
            } else {
                System.out.println("Количество людей должно быть больше 1. ");
            }
        }
        while (true) {
            System.out.println("Введите название товара или 'Завершить', чтобы завершить ввод:");
            String itemName = scanner.next().toLowerCase();

            // Проверка на завершение ввода
            if (itemName.equalsIgnoreCase("завершить")) {
                break;
            }
            System.out.println("Введите стоимость товара в формате рубли.копейки:");
            double itemPrice = getInputDouble();
            Item item = new Item(itemName, itemPrice); // Создание и добавление товара в калькулятор
            items[itemCount++] = item;
            System.out.println("Товар '" + item.getName() + "' успешно добавлен.");
            System.out.println("Хотите добавить ещё один товар?");
            scanner.nextLine(); // Очищаем буфер
        }
        displayResults(); // Вывод результатов
    }
    // Получение целого числа с проверкой корректности ввода
    private int getInputInt() {
        while (true) {
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input > 0) {
                    return input;
                } else {
                    System.out.println("Количество людей должно быть больше 1. ");
                }
            } else {
                System.out.println("Некорректный ввод. Пожалуйста, введите целое число.");
                scanner.next(); // Очищаем буфер
            }
        }
    }
    // Получение дробного числа с проверкой корректности ввода
    private double getInputDouble() {
        while (true) {
            if (scanner.hasNextDouble()) {
                double input = scanner.nextDouble();
                if (input >= 0) {
                    return input;
                } else {
                    System.out.println("Стоимость товара не может быть отрицательной");
                }
            } else {
                System.out.println("Некорректный ввод. Пожалуйста, введите число.");
                scanner.next(); // Очищаем буфер
            }
        }
    }

    // Вывод результатов
    private void displayResults() {
        System.out.println("Добавленные товары:");
        for (int i = 0; i < itemCount; i++) {
            System.out.println(items[i].getName() + ": " + RubleChange.formatCurrency(items[i].getPrice()));
        }

        double totalCost = calculateTotalCost();
        double costPerPerson = totalCost / numberOfPeople;

        System.out.println("\nОбщая стоимость: " + RubleChange.formatCurrency(totalCost));
        System.out.println("Каждый человек должен заплатить: " + RubleChange.formatCurrency(costPerPerson));
    }

    // Подсчет общей стоимости товаров
    private double calculateTotalCost() {
        double totalCost = 0;
        for (int i = 0; i < itemCount; i++) {
            totalCost = totalCost + items[i].getPrice();
        }
        return totalCost;
    }
}

// Товар
class Item {
    private String name; // Название товара
    private double price; // Стоимость товара

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
}

// Форматирование для правильного окончания
class RubleChange {
    public static String formatCurrency(double amount) {
        int intValue = (int) amount; // Убираем дробную часть
        int lastDigit = intValue % 10; // Получаем последнюю цифру числа

        if (lastDigit == 1 && intValue != 11) {
            return String.format("%.2f рубль", amount);
        } else if ((lastDigit >= 2 && lastDigit <= 4) && (intValue < 12 || intValue > 14)) {
            return String.format("%.2f рубля", amount);
        } else {
            return String.format("%.2f рублей", amount);
        }
    }
}