/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OptimizingNetworkGraphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class Diversion {

    public Diversion(Arc arc, boolean started) {
        this.arc = arc;
        this.started = started;
    }

    private Arc arc;
    private boolean started;

    public Arc Arc() {
        return arc;
    }

    public void Arc(Arc arc) {
        this.arc = arc;
    }

    public boolean Started() {
        return started;
    }

    public void Started(boolean started) {
        this.started = started;
    }

    public Diversion(Arc arc) {
        this.arc = arc;
        this.started = false;
    }

    public static void Sort(List<Diversion> arcs) {
        int start;
        List<Diversion> result = new ArrayList<>();
        for (Diversion d : arcs) {
            if (d.started) {
                result.add(d);
            }
        }
        for(Diversion r:result){
            Diversion.Remove(arcs, r);
        }
        Collections.sort(arcs, new Comparator<Diversion>() {
            public int compare(Diversion d1, Diversion d2) {
                return d1.arc.Reserve() - d2.arc.Reserve();
            }
        });
        for(Diversion a:arcs){
            result.add(a);
        }
        arcs.removeAll(arcs);
        for(Diversion r:result){
            arcs.add(r);
        }
        arcs=result;
    }
    
    public static void Remove(List<Diversion> diversions,Diversion d){
        Arc a1=d.Arc();
        boolean st1=d.Started();
        for(int i=0;i<diversions.size();i++){
            Arc a2=diversions.get(i).Arc();
            boolean st2=diversions.get(i).Started();
            if(a1.Start()==a2.Start()&&a1.End()==a2.End()&&st1==st2){
                diversions.remove(i);
                i--;
            }
        }
    }
}
