import java.math.BigDecimal;
import java.util.Scanner;

// Базовый класс для налогов
abstract class TaxType {
    public abstract BigDecimal calculateTaxFor(BigDecimal amount);

    public abstract BigDecimal getTaxRate(BigDecimal amount);
}

// НДФЛ для граждан РФ с прогрессивной ставкой
class IncomeTaxType extends TaxType {
    // Сколько уплачено
    @Override
    public BigDecimal calculateTaxFor(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(2400000)) <= 0) {
            return amount.multiply(BigDecimal.valueOf(0.13));
        } else if (amount.compareTo(BigDecimal.valueOf(5000000)) <= 0) {
            return BigDecimal.valueOf(312000).add(amount.subtract(BigDecimal.valueOf(2400000)).multiply(BigDecimal.valueOf(0.15)));
        } else if (amount.compareTo(BigDecimal.valueOf(20000000)) <= 0) {
            return BigDecimal.valueOf(702000).add(amount.subtract(BigDecimal.valueOf(5000000)).multiply(BigDecimal.valueOf(0.18)));
        } else if (amount.compareTo(BigDecimal.valueOf(50000000)) <= 0) {
            return BigDecimal.valueOf(3402000).add(amount.subtract(BigDecimal.valueOf(20000000)).multiply(BigDecimal.valueOf(0.20)));
        } else {
            return BigDecimal.valueOf(9402000).add(amount.subtract(BigDecimal.valueOf(50000000)).multiply(BigDecimal.valueOf(0.22)));
        }
    }

    // Какой % уплачен
    @Override
    public BigDecimal getTaxRate(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(2400000)) <= 0) {
            return BigDecimal.valueOf(13);
        } else if (amount.compareTo(BigDecimal.valueOf(5000000)) <= 0) {
            return BigDecimal.valueOf(15);
        } else if (amount.compareTo(BigDecimal.valueOf(20000000)) <= 0) {
            return BigDecimal.valueOf(18);
        } else if (amount.compareTo(BigDecimal.valueOf(50000000)) <= 0) {
            return BigDecimal.valueOf(20);
        } else {
            return BigDecimal.valueOf(22);
        }
    }
}

// НДС
class VATaxType extends TaxType {
    // Сколько уплачено
    @Override
    public BigDecimal calculateTaxFor(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(60000000)) < 0) {
            return BigDecimal.ZERO; // Освобождение от НДС
        } else if (amount.compareTo(BigDecimal.valueOf(250000000)) <= 0) {
            return amount.multiply(BigDecimal.valueOf(0.05));
        } else if (amount.compareTo(BigDecimal.valueOf(450000000)) <= 0) {
            return amount.multiply(BigDecimal.valueOf(0.07));
        } else {
            return amount.multiply(BigDecimal.valueOf(0.20)); // Обычная ставка
        }
    }

    //Какой % уплачен
    @Override
    public BigDecimal getTaxRate(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(60000000)) < 0) {
            return BigDecimal.ZERO;
        } else if (amount.compareTo(BigDecimal.valueOf(250000000)) <= 0) {
            return BigDecimal.valueOf(5);
        } else if (amount.compareTo(BigDecimal.valueOf(450000000)) <= 0) {
            return BigDecimal.valueOf(7);
        } else {
            return BigDecimal.valueOf(20);
        }
    }
}

// НДФЛ для иностранцев
class ForeignIncomeTaxType extends TaxType {
    //Сколько уплачено
    @Override
    public BigDecimal calculateTaxFor(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(2400000)) <= 0) {
            return amount.multiply(BigDecimal.valueOf(0.13));
        } else if (amount.compareTo(BigDecimal.valueOf(5000000)) <= 0) {
            return BigDecimal.valueOf(312000).add(amount.subtract(BigDecimal.valueOf(2400000)).multiply(BigDecimal.valueOf(0.15)));
        } else if (amount.compareTo(BigDecimal.valueOf(20000000)) <= 0) {
            return BigDecimal.valueOf(702000).add(amount.subtract(BigDecimal.valueOf(5000000)).multiply(BigDecimal.valueOf(0.18)));
        } else if (amount.compareTo(BigDecimal.valueOf(50000000)) <= 0) {
            return BigDecimal.valueOf(3402000).add(amount.subtract(BigDecimal.valueOf(20000000)).multiply(BigDecimal.valueOf(0.20)));
        } else {
            return BigDecimal.valueOf(9402000).add(amount.subtract(BigDecimal.valueOf(50000000)).multiply(BigDecimal.valueOf(0.22)));
        }
    }

    // Какой % уплачен
    @Override
    public BigDecimal getTaxRate(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(2400000)) <= 0) {
            return BigDecimal.valueOf(13);
        } else if (amount.compareTo(BigDecimal.valueOf(5000000)) <= 0) {
            return BigDecimal.valueOf(15);
        } else if (amount.compareTo(BigDecimal.valueOf(20000000)) <= 0) {
            return BigDecimal.valueOf(18);
        } else if (amount.compareTo(BigDecimal.valueOf(50000000)) <= 0) {
            return BigDecimal.valueOf(20);
        } else {
            return BigDecimal.valueOf(22);
        }
    }
}

public class Taxes {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите ваш доход (в рублях): ");
        BigDecimal income = scanner.nextBigDecimal();

        System.out.println("Выберите тип налогоплательщика:");
        System.out.println("1 - Физическое лицо (гражданин РФ)");
        System.out.println("2 - Иностранное лицо");
        System.out.println("3 - Юридическое лицо/ИП");
        int choice = scanner.nextInt();

        TaxType taxType;
        switch (choice) {
            case 1 -> taxType = new IncomeTaxType();
            case 2 -> taxType = new ForeignIncomeTaxType();
            case 3 -> taxType = new VATaxType();
            default -> {
                System.out.println("Неверный выбор.");
                return;
            }
        }

        BigDecimal tax = taxType.calculateTaxFor(income);
        BigDecimal rate = taxType.getTaxRate(income);

        System.out.printf("Уплачен налог: %.2f руб. (Ставка: %.0f%%)%n", tax, rate);
    }
}
