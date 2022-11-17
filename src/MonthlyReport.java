
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;


public class MonthlyReport {

    HashMap<Integer, MonthData> monthExpensesAndIncome = new HashMap<>();

    public void getExpensesAndIncome(String pathYear, String pathMonth) {
        int m = Integer.parseInt(pathMonth);
        for (int i = 1; i <= m; i++) {
            double sumExp = 0;
            double sumInc = 0;
            Integer month = i;
            String path = "resources/m." + pathYear + "0" + month + ".csv";
            readMonthFileContentsOrNull(path);
            if (readMonthFileContentsOrNull(path) != null) {
                String[] lines = readMonthFileContentsOrNull(path).split("\n");
                for (int j = 1; j != lines.length; j++) {
                    String line = lines[j];
                    String[] lineContents = line.split(",");
                    String itName = lineContents[0];
                    boolean iExpense = Boolean.parseBoolean(lineContents[1]);
                    int quan = Integer.parseInt(lineContents[2]);
                    double suOfOne = Integer.parseInt(lineContents[3]);

                    if (!monthExpensesAndIncome.containsKey(month)) {
                        monthExpensesAndIncome.put(month, new MonthData(month));
                    }

                    MonthData monthData = monthExpensesAndIncome.get(month);
                    monthData.itemName.add(itName);
                    monthData.isExpense.add(iExpense);
                    monthData.quantity.add(quan);
                    monthData.sumOfOne.add(suOfOne);

                    if (iExpense) {
                        sumExp += suOfOne * quan;
                    } else {
                        sumInc += suOfOne * quan;
                    }
                    monthData.commonExpenses = sumExp;
                    monthData.commonIncome = sumInc;
                }
            }
        }
        if (!monthExpensesAndIncome.isEmpty()){
            System.out.println("Файл считан");
        }else System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории");
    }

    public String readMonthFileContentsOrNull(String path){

        try {
            return Files.readString(Path.of(path));
        }
        catch (IOException e) {
            return null;
        }
    }

    public void printMonthReport(){
        if (!monthExpensesAndIncome.isEmpty()) {
            System.out.println("Январь");
            System.out.println("Самый прибыльный товар: " + bestItem(1));
            System.out.println("Самая большая трата составила: " + maxExpense(1));
            System.out.println("Февраль");
            System.out.println("Самый прибыльный товар: " + bestItem(2));
            System.out.println("Самая большая трата составила: " + maxExpense(2));
            System.out.println("Март");
            System.out.println("Самый прибыльный товар: " + bestItem(3));
            System.out.println("Самая большая трата составила: " + maxExpense(3));
        } else System.out.println("Файлы месячеых отчётов не были считаны");
    }

    public HashMap <String, Double> bestItem(int month){
        HashMap <String, Double> bestItem = new HashMap<>();
        String itName = "";
        double maxSum = 0;
            MonthData monthData = monthExpensesAndIncome.get(month);
            for (int i = 0; i < monthData.quantity.size(); i++) {
                if (!monthData.isExpense.get(i)){
                    double sum = monthData.quantity.get(i) * monthData.sumOfOne.get(i);
                    if (sum > maxSum) {
                        maxSum = sum;
                        itName = monthData.itemName.get(i);
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
            MonthData monthData = monthExpensesAndIncome.get(month);
            for (int i = 0; i < monthData.quantity.size(); i++){
                if (monthData.isExpense.get(i)){
                    double sum = monthData.quantity.get(i) * monthData.sumOfOne.get(i);
                    if (sum > mExpense) {
                        mExpense = sum;
                        itName = monthData.itemName.get(i);
                    }
                }
            }
        maxExpense.put(itName, mExpense);
        return maxExpense;
    }

    public double monthlyExpense(int month){
        MonthData monthData = monthExpensesAndIncome.get(month);
        return monthData.commonExpenses;
    }

    public double monthlyIncome(int month){
        MonthData monthData = monthExpensesAndIncome.get(month);
        return monthData.commonIncome;
    }
}
