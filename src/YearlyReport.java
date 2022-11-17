
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;


public class YearlyReport {
    HashMap<Integer, YearData> yearExpensesAndIncome;

    public YearlyReport() {
        yearExpensesAndIncome = new HashMap<>();
    }

    public void getExpensesAndIncome(String pathYear) {

        readFileContentsOrNull(pathYear);
        if (readFileContentsOrNull(pathYear) != null) {
            String[] lines = readFileContentsOrNull(pathYear).split("\n");
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                String[] lineContents = line.split(",");
                Integer month = Integer.parseInt(lineContents[0]);
                double amount = Double.parseDouble(lineContents[1]);
                boolean isExpense = Boolean.parseBoolean(lineContents[2]);

                if (!yearExpensesAndIncome.containsKey(month)) {
                    yearExpensesAndIncome.put(month, new YearData(month));
                }

                YearData yearData = yearExpensesAndIncome.get(month);
                if (isExpense) {
                    yearData.expenses = amount;
                } else {
                    yearData.income = amount;
                }
            }
            System.out.println("Файл считан");
        } else System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории");
    }

    public String readFileContentsOrNull(String pathYear){

        try {
            String path = "resources/y." + pathYear + ".csv";
            return Files.readString(Path.of(path));
        }
        catch (IOException e) {
            return null;
        }
    }

    public void printYearInfo(String pathYear){
        if (!yearExpensesAndIncome.isEmpty()) {
            System.out.println("Рассматриваемый год: " + pathYear);
            profitPerMonth();
            midExpense();
            midIncome();
        } else System.out.println("Файл годового отчёта не был считан");
    }
    public void profitPerMonth() {
        double profit = 0;
        for (int months: yearExpensesAndIncome.keySet()) {
            YearData yearData = yearExpensesAndIncome.get(months);
            double difference = yearData.income - yearData.expenses;
            profit += difference;
            System.out.println("Прибыль за " + months + " месяц: " + profit);
        }
    }

    public void midExpense(){
        double sum = 0;
        for (int months: yearExpensesAndIncome.keySet()) {
            YearData yearData = yearExpensesAndIncome.get(months);
            sum += yearData.expenses;
        }
        double mid = sum / yearExpensesAndIncome.size();
        System.out.println("Средний расход за все месяцы в году: " + mid);
    }

    public void midIncome(){
        double sum = 0;
        for (int months: yearExpensesAndIncome.keySet()) {
            YearData yearData = yearExpensesAndIncome.get(months);
            sum += yearData.income;
        }
        double mid = sum / yearExpensesAndIncome.size();
        System.out.println("Средний доход за все месяцы в году: " + mid);
    }

    public double yearlyExpense(int month) {
        YearData yearData = yearExpensesAndIncome.get(month);
        return yearData.expenses;
    }

    public double yearlyIncome(int month){
        YearData yearData = yearExpensesAndIncome.get(month);
        return yearData.income;
    }
}
