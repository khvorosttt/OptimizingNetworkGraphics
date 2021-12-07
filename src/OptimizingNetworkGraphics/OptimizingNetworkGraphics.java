/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OptimizingNetworkGraphics;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class OptimizingNetworkGraphics {

    static int tops;
    static int arc;
    static int criticalPath;
    static int R;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        List<Arc> arcs = In();
        Critical_pathFirst(arcs);
        System.out.println("Критический срок t = "+criticalPath);
        Reserve(arcs);
        System.out.println("");
        System.out.println("");
        Analysis(arcs);
        System.out.println("");
        Critical_path(arcs);
        System.out.println("Критический срок t = "+criticalPath);
    }

    //заполнение данными
    public static List<Arc> In() throws FileNotFoundException, IOException {
        BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\Вариант10.txt")));
        String[] data_info = fin.readLine().split(" ");//количество вершин и дуг
        tops = Integer.parseInt(data_info[0]);
        arc = Integer.parseInt(data_info[1]);
        R = Integer.parseInt(data_info[2]);
        String[] data = fin.readLine().split(" ");
        int[][] matrix_info = new int[Integer.parseInt(data_info[1])][4];
        for (int i = 0; i < matrix_info.length - 1; i++) {
            for (int j = 0; j < 4; j++) {
                matrix_info[i][j] = Integer.parseInt(data[j]);
            }
            data = fin.readLine().split(" ");
        }
        for (int j = 0; j < 4; j++) {
            matrix_info[matrix_info.length - 1][j] = Integer.parseInt(data[j]);
        }
        showMatrix(matrix_info);
        List<Arc> arcs = new ArrayList<Arc>();
        for (int i = 0; i < matrix_info.length; i++) {
            Arc temp = new Arc(matrix_info[i][0], matrix_info[i][1], matrix_info[i][2], matrix_info[i][3]);
            int temp_start = 0;
            for (Arc a : arcs) {
                if (a.End() == matrix_info[i][0]) {
                    if (temp_start < a.OtEnd()) {
                        temp_start = a.OtEnd();
                    }
                }
            }
            temp.OtStart(temp_start);
            temp.OtEnd(temp.OtStart() + matrix_info[i][2]);
            arcs.add(temp);
        }
        Collections.sort(arcs, new Comparator<Arc>() {
            public int compare(Arc a1, Arc a2) {
                return a1.Start() - a2.Start();
            }
        });
        for (Arc a : arcs) {
            System.out.println(a.Start() + " " + a.End() + " " + a.OtStart() + " " + a.OtEnd());
        }
        System.out.println();
        return arcs;
    }

    //нахождение критического пути в конце
    public static void Critical_pathFirst(List<Arc> arcs) {
        List<Arc> critical_path = new ArrayList<Arc>();
        criticalPath = -1;
        Arc temp = null;
        for (Arc a : arcs) {
            if (criticalPath < a.OtEnd()) {
                temp = a;
                criticalPath = temp.OtEnd();
            }
        }
        Reserve(arcs);
        critical_path.add(temp);
        while ((temp = critical_path.get(critical_path.size() - 1)).Start() != 1||temp.OtStart()!=0) {
            for (Arc a : arcs) {
                if (temp.OtStart() == a.OtEnd()&&a.Reserve()==0) {
                    critical_path.add(a);
                    break;
                }
            }
        }
        Collections.reverse(critical_path);
        System.out.println("Критический путь ");
        for (Arc a : critical_path) {
            System.out.println(a.Start() + " " + a.End() + " " + a.OtStart() + " " + a.OtEnd());
        }
    }
    //нахождение критического пути в конце
    public static void Critical_path(List<Arc> arcs) {
        List<Arc> critical_path = new ArrayList<Arc>();
        criticalPath = -1;
        Arc temp = null;
        for (Arc a : arcs) {
            if (criticalPath < a.OtEnd()) {
                temp = a;
                criticalPath = temp.OtEnd();
            }
        }
        critical_path.add(temp);
        while ((temp = critical_path.get(critical_path.size() - 1)).Start() != 1||temp.OtStart()!=0) {
            for (Arc a : arcs) {
                if (temp.OtStart() == a.OtEnd()) {
                    critical_path.add(a);
                    break;
                }
            }
        }
        Collections.reverse(critical_path);
        System.out.println("Критический путь ");
        for (Arc a : critical_path) {
            System.out.println(a.Start() + " " + a.End() + " " + a.OtStart() + " " + a.OtEnd());
        }
    } 

    //показ матрицы
    public static void showMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    //нахождение резервных дней
    public static void Reserve(List<Arc> arcs) {
        Collections.reverse(arcs);
        for(Arc a:arcs){
            a.Reserve(0);
        }
        for (Arc a : arcs) {
            if (a.End() == tops) {
                a.Reserve(criticalPath - a.OtEnd());
            } else {
                int min = Integer.MAX_VALUE, min_otStart = 0;
                for (Arc b : arcs) {
                    if (a.End() == b.Start()) {
                        if (min > b.Reserve()) {
                            min = b.Reserve();
                            min_otStart = b.OtStart();
                        }
                        a.Reserve((b.OtStart() - a.OtEnd()) + b.Reserve());
                    }
                }
                a.Reserve((min_otStart - a.OtEnd()) + min);
            }
        }
        Collections.reverse(arcs);
    }
    
    //показ резервных дней
    public static void showReserve(List<Arc> arcs){
        for (Arc a : arcs) {
            System.out.println(a.Start() + " " + a.End() + " " + a.Reserve());
        }
    }

    //сдвиги по оси Ot
    public static void Analysis(List<Arc> arcs) {
        List<Arc> result = new ArrayList<>();//вспомогательный лист для записи результата
        List<Diversion> temp = new ArrayList<>();//вспомогательный лист, содержащий дуги, которые расположены на данном промежутке
        int start = 0, end = EndOfTheGap(arcs, temp);
        while (!arcs.isEmpty()||!temp.isEmpty()) {
            for (Arc a : arcs) {
                if (a.OtStart() < end) {//включение в вспомогательный лист дуг, расположенных на отрезке [start;end]
                    temp.add(new Diversion(a));
                }
            }
            for(Diversion d: temp){//удаление из основного листадуг, которые включили в вспомогательный
                arcs.remove(d.Arc());
            }
            Diversion.Sort(temp);//сортировка вспомогательного листа по резервным дням
            int sum = 0;//переменная для подсчёта суммы ресурсов на промежутке
            for (Diversion d : temp) {
                if (sum + d.Arc().Resource() <= R) {
                    sum += d.Arc().Resource();
                    d.Started(true);//если сумма не превышает заданную величину, начинаем выполнение работы на промежутке [start;end]
                }
            }
            for (Diversion d : temp) {
                if (d.Started()) {//работа начата
                    if (d.Arc().OtEnd() == end) {//если конец начатой работы совпадает с концом рассматриваемого промежутка, то включаем дугу в лист результата(работа выполнена)
                        result.add(d.Arc());
                    }
                } else {//работа не начата
                    d.Arc().Reserve(d.Arc().Reserve() - (end - d.Arc().OtStart()));//новые значения резервных дней
                    d.Arc().OtStart(end);//сдвигаем начало дуга в значение end
                    d.Arc().OtEnd(d.Arc().OtStart() + d.Arc().Time());//определяем конец дуги
                }
            }
            for(Arc a:result){//удаляем из вспомогательного листа дуги, включенные в лист-результат
                Diversion.Remove(temp,new Diversion(a,true));
            }
            start = end;
            end = EndOfTheGap(arcs, temp);
        }
        for (Arc r : result) {
            System.out.println(r.Start() + " " + r.End() + " " + r.OtStart() + " " + r.OtEnd());
        }
        arcs.addAll(result);
    }

    /**
     * 
     * @param arcs Начальный лист дуг
     * @param diversions Вспомогательный лист дуг, содержащий дуги принадлежащие промежутку [start;end]
     * @return Конец нового промежутка
     * Среди концов дуг листов arcs,diversions находится минимальный. Он становится концом нового рассматриваемого промежутка
     */
    public static int EndOfTheGap(List<Arc> arcs, List<Diversion> diversions) {
        int min = Integer.MAX_VALUE;
            for (Arc a : arcs) {
                if (min > a.OtEnd()) {
                    min = a.OtEnd();
                }
            }
            for (Diversion d : diversions) {
                if (min > d.Arc().OtEnd()) {
                    min = d.Arc().OtEnd();
                }
            }
        return min;
    }
}
