import java.util.ArrayList;

public class MonthData {
    int month;
    ArrayList <String> itemName = new ArrayList<>();
    ArrayList <Boolean> isExpense = new ArrayList<>();
    ArrayList <Integer> quantity = new ArrayList<>();
    ArrayList <Double> sumOfOne = new ArrayList<>();
    double commonExpenses;
    double commonIncome;

    public MonthData(int month){
        this.month = month;
    }

}
