import java.util.HashMap;

public class DataReconciliation {

    public void reconciliation(HashMap<Integer, MonthData> month, HashMap<Integer, YearData> year) {
        if (!month.isEmpty()){
            if (!year.isEmpty()){
                if (month.get(1).commonExpenses == year.get(1).expenses && month.get(1).commonIncome == year.get(1).income){
                    if (month.get(2).commonExpenses == year.get(2).expenses && month.get(2).commonIncome == year.get(2).income){
                        if (month.get(3).commonExpenses == year.get(3).expenses && month.get(3).commonIncome == year.get(3).income){
                            System.out.println("Операция успешно завершена");
                        } else System.out.println("В " + 3 + " месяце найдено несоответсвие");
                    } else System.out.println("Во " + 2 + " месяце найдено несоответсвие");
                } else System.out.println("В " + 1 + " месяце найдено несоответсвие");
            } else System.out.println("Файл годового отчёта не был считан");
        } else System.out.println("Файлы месячных отчетов не были считаны");
    }
}


