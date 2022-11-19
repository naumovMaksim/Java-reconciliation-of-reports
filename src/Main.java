
import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ReportEngine reportEngine = new ReportEngine();


        while (true){
            printMenu();
            int userInput = scanner.nextInt();
            String pathY = "2021"; // рассматриваемый год
            String pathM = "3"; // колличество загруженных месяцев

            if (userInput == 1){
                reportEngine.getMonthExpensesAndIncome(pathY, pathM);
            } else if (userInput == 2) {
                reportEngine.getYearExpensesAndIncome(pathY);
            } else if (userInput == 3) {
                reportEngine.reconciliation();
            } else if (userInput == 4) {
                reportEngine.printMonthReport();
            } else if (userInput == 5) {
                reportEngine.printYearInfo(pathY);
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

