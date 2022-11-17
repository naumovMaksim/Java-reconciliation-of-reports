
import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        DataReconciliation dataReconciliation = new DataReconciliation();


        while (true){
            printMenu();
            int userInput = scanner.nextInt();
            String pathYear = "2021";
            String pathMonth = "3";

            if (userInput == 1){
                monthlyReport.getExpensesAndIncome(pathYear, pathMonth);
            } else if (userInput == 2) {
                yearlyReport.getExpensesAndIncome(pathYear);
            } else if (userInput == 3) {
                dataReconciliation.reconciliation(monthlyReport.monthExpensesAndIncome, yearlyReport.yearExpensesAndIncome);
            } else if (userInput == 4) {
                monthlyReport.printMonthReport();
            } else if (userInput == 5) {
                yearlyReport.printYearInfo(pathYear);
            } else if (userInput == 0) {
                System.out.println("Программа завершена");
                break;
            } else  System.out.println("Извините, такой команды нет.");
        }
        
    }
    private static void printMenu(){
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выйти из приложения");
    }
}

