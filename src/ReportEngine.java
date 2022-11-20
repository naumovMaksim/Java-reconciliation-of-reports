
import java.util.HashMap;
import java.util.List;

public class ReportEngine {
    Reader reader = new Reader();
    HashMap<Integer, MonthlyReport> monthlyReports = new HashMap<>();
    YearlyReport yearlyReport;

    public void getYearExpensesAndIncome(String pathYear) {
        HashMap<Integer, YearData> yearExpensesAndIncome = new HashMap<>();
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
            int y = Integer.parseInt(pathYear);
            yearlyReport = new YearlyReport();
            yearlyReport.year = y;
            yearlyReport.yearExpensesAndIncome = yearExpensesAndIncome;
            System.out.println("Файл считан");
        }
    }

    public void getMonthExpensesAndIncome(String pathY, String pathM) {
        HashMap<Integer, HashMap<String, MonthData>> monthExpensesAndIncome = new HashMap<>();
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

                }
                monthExpensesAndIncome.put(i, item);
                int y = Integer.parseInt(pathY);
                MonthlyReport monthlyReport = new MonthlyReport();
                monthlyReport.month = i;
                monthlyReport.year = y;
                monthlyReport.monthExpensesAndIncome = monthExpensesAndIncome;
                monthlyReports.put(i, monthlyReport);
            } else break;
        }
        if (!monthExpensesAndIncome.isEmpty()){
            System.out.println("Файл считан");
        }
    }

    public void printYearInfo(String pathYear){
        if (yearlyReport.yearExpensesAndIncome != null) {
            System.out.println(pathYear);
            profitPerMonth();
            midExpense();
            midIncome();
        } else System.out.println("Файл годового отчёта не был считан");
    }

    public void printMonthReport(){
        if (monthlyReports.isEmpty()) {
            System.out.println("Файлы месячеых отчётов не были считаны");
        } else {
            for (int month: monthlyReports.keySet()) {
                System.out.println(getMonthName(month - 1));
                System.out.println("Самый прибыльный товар: " + bestItem(month));
                System.out.println("Самая большая трата составила: " + maxExpense(month));// Посмотрел enum, показалось немного сложным пока что, решил оставвить так:)
            }
        }
    }

    public void profitPerMonth() {
        double profit = 0;
        for (int months: yearlyReport.yearExpensesAndIncome.keySet()) {
            YearData yearData = yearlyReport.yearExpensesAndIncome.get(months);
            double difference = yearData.income - yearData.expenses;
            profit += difference;
            System.out.println("Прибыль за " + months + " месяц: " + profit);
        }
    }

    public void midExpense(){
        double sum = 0;
        for (int months: yearlyReport.yearExpensesAndIncome.keySet()) {
            YearData yearData = yearlyReport.yearExpensesAndIncome.get(months);
            sum += yearData.expenses;
        }
        double mid = sum / yearlyReport.yearExpensesAndIncome.size();
        System.out.println("Средний расход за все месяцы в году: " + mid);
    }

    public void midIncome(){
        double sum = 0;
        for (int months: yearlyReport.yearExpensesAndIncome.keySet()) {
            YearData yearData = yearlyReport.yearExpensesAndIncome.get(months);
            sum += yearData.income;
        }
        double mid = sum / yearlyReport.yearExpensesAndIncome.size();
        System.out.println("Средний доход за все месяцы в году: " + mid);
    }

    public HashMap <String, Double> bestItem(int month){
        HashMap <String, Double> bestItem = new HashMap<>();

        String itName = "";
        double maxSum = 0;
        HashMap <String, MonthData> item = monthlyReports.get(month).monthExpensesAndIncome.get(month);
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
        HashMap <String, MonthData> item = monthlyReports.get(month).monthExpensesAndIncome.get(month);
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
        for (int months: monthlyReports.keySet()) {
            HashMap<String, MonthData> item = monthlyReports.get(months).monthExpensesAndIncome.get(months);
            double allExpense = 0;
            for (MonthData one : item.values()) {
                if (one.isExpense) {
                    allExpense += one.quantity * one.sumOfOne;
                }
            }
            allMonthExpenses.put(months, allExpense);
        }
        return allMonthExpenses;
    }

    public HashMap<Integer, Double> allMonthIncome(){
        HashMap <Integer, Double> allMonthIncome = new HashMap<>();
        for (int months: monthlyReports.keySet()) {
            HashMap<String, MonthData> item = monthlyReports.get(months).monthExpensesAndIncome.get(months);
            double allIncome = 0;
            for (MonthData one : item.values()) {
                if (!one.isExpense) {
                    allIncome += one.quantity * one.sumOfOne;
                }
            }
            allMonthIncome.put(months, allIncome);
        }
        return allMonthIncome;
    }

    public void reconciliation(){
        boolean completed = false;
        for (int month: monthlyReports.keySet()) {
            if (!monthlyReports.isEmpty() && !yearlyReport.yearExpensesAndIncome.isEmpty()) {
                    YearData yearData = yearlyReport.yearExpensesAndIncome.get(month);
                    if (allMonthExpenses().get(month) == yearData.expenses && allMonthIncome().get(month) == yearData.income) {
                        completed = true;
                    } else System.out.println("В " + month + " месяце ошибка");
            }
        }
        if (completed) {
            System.out.println("Операция успешно завершена");
        }else System.out.println("Файлы годового или месячных отчётов не были считаны");
    }

    public String getMonthName(int month){
        String [] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        return months[month];
    }
}
