
import java.util.HashMap;
import java.util.List;

public class ReportEngine {
    Reader reader = new Reader();

    HashMap<Integer, HashMap<String, MonthData>> monthExpensesAndIncome;
    HashMap<Integer, YearData> yearExpensesAndIncome;

    public  void getYearExpensesAndIncome(String pathYear) {
        yearExpensesAndIncome = new HashMap<>();
        List<String> lines = reader.readFileContentsOrNull( "resources/y." + pathYear + ".csv");
        if (lines.size() != 0) {
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
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

    public void getMonthExpensesAndIncome(String pathY, String pathM) {
        monthExpensesAndIncome = new HashMap<>();
        int months = Integer.parseInt(pathM);
        for (int i = 1; i <= months; i++) {
            HashMap<String, MonthData> item = new HashMap<>();
            List<String> lines = reader.readFileContentsOrNull("resources/m." + pathY + "0" + i + ".csv");
            if (lines.size() != 0) {
                for (int j = 1; j != lines.size(); j++) {
                    MonthData monthData = new MonthData();
                    String line = lines.get(j);
                    String[] lineContents = line.split(",");
                    String itName = lineContents[0];
                    boolean iExpense = Boolean.parseBoolean(lineContents[1]);
                    int quan = Integer.parseInt(lineContents[2]);
                    int suOfOne = Integer.parseInt(lineContents[3]);

                    monthData.itemName = itName;
                    monthData.isExpense = iExpense;
                    monthData.quantity = quan;
                    monthData.sumOfOne = suOfOne;

                    item.put(itName, monthData);

                } monthExpensesAndIncome.put(i, item);
            }
        }
        if (!monthExpensesAndIncome.isEmpty()){
            System.out.println("Файл считан");
        }else System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории");
    }

    public void printYearInfo(String pathYear){
        if (yearExpensesAndIncome != null) {
            System.out.println(pathYear);
            profitPerMonth();
            midExpense();
            midIncome();
        } else System.out.println("Файл годового отчёта не был считан");
    }

    public void printMonthReport(){
        if (monthExpensesAndIncome == null) {
            System.out.println("Файлы месячеых отчётов не были считаны");
        } else {
            for (int month: monthExpensesAndIncome.keySet()) {
                if (month == 1) {
                    System.out.println("Январь");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
                if (month == 2) {
                    System.out.println("Февраль");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
                if (month == 3) {
                    System.out.println("Март");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
                if (month == 4) {
                    System.out.println("Апрель");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
                if (month == 5) {
                    System.out.println("Май");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
                if (month == 6) {
                    System.out.println("Июнь");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
                if (month == 7) {
                    System.out.println("Июль");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
                if (month == 8) {
                    System.out.println("Август");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
                if (month == 9) {
                    System.out.println("Сентябрь");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
                if (month == 10) {
                    System.out.println("Октябрь");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
                if (month == 11) {
                    System.out.println("Ноябрь");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
                if (month == 12) {
                    System.out.println("Декабрь");
                    System.out.println("Самый прибыльный товар: " + bestItem(month));
                    System.out.println("Самая большая трата составила: " + maxExpense(month));
                }
            }
        }
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

    public HashMap <String, Double> bestItem(int month){
        HashMap <String, Double> bestItem = new HashMap<>();
        String itName = "";
        double maxSum = 0;
        HashMap <String, MonthData> item = monthExpensesAndIncome.get(month);
        for (MonthData one: item.values()) {
            if (!one.isExpense){
                double sum = one.quantity * one.sumOfOne;
                if (sum > maxSum) {
                    maxSum = sum;
                    itName = one.itemName;
                }
            }
        }
        bestItem.put(itName, maxSum);
        return bestItem;
    }

    public HashMap <String, Double> maxExpense(int month){
        HashMap <String, Double> maxExpense = new HashMap<>();
        double mExpense = 0;
        String itName = "";
        HashMap <String, MonthData> item = monthExpensesAndIncome.get(month);
        for (MonthData one: item.values()) {
            if (one.isExpense){
                double sum = one.quantity * one.sumOfOne;
                if (sum > mExpense) {
                    mExpense = sum;
                    itName = one.itemName;
                }
            }
        }
        maxExpense.put(itName, mExpense);
        return maxExpense;
    }

    public HashMap<Integer, Double> allMonthExpenses(){
        HashMap <Integer, Double> allMonthExpenses = new HashMap<>();
        for (int month: monthExpensesAndIncome.keySet()) {
            HashMap<String, MonthData> item = monthExpensesAndIncome.get(month);
            double allExpense = 0;
            for (MonthData one : item.values()) {
                if (one.isExpense) {
                    allExpense += one.quantity * one.sumOfOne;
                }
            }
            allMonthExpenses.put(month, allExpense);
        }
        return allMonthExpenses;
    }

    public HashMap<Integer, Double> allMonthIncome(){
        HashMap <Integer, Double> allMonthIncome = new HashMap<>();
        for (int month: monthExpensesAndIncome.keySet()) {
            HashMap<String, MonthData> item = monthExpensesAndIncome.get(month);
            double allIncome = 0;
            for (MonthData one : item.values()) {
                if (!one.isExpense) {
                    allIncome += one.quantity * one.sumOfOne;
                }
            }
            allMonthIncome.put(month, allIncome);
        }
        return allMonthIncome;
    }

    public void reconciliation(){
        boolean completed = false;
        if (monthExpensesAndIncome != null && yearExpensesAndIncome != null) {
            for (int month: monthExpensesAndIncome.keySet()) {
                YearData yearData = yearExpensesAndIncome.get(month);
                if (allMonthExpenses().get(month) == yearData.expenses && allMonthIncome().get(month) == yearData.income) {
                    completed = true;
                } else System.out.println("В " + month + " месяце ошибка");
            }
            if (completed){
                System.out.println("Операция успешно завершена");
            }
        } else System.out.println("Файлы годового или месячных отчётов не были считаны");
    }
}
