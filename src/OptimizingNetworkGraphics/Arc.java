/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OptimizingNetworkGraphics;

/**
 *
 * @author Lenovo
 */
public class Arc {
    
    //начало
    private int start;
    //конец
    private int end;
    //время(дни)
    private int time;
    //ресурсы
    private int resource;
    //начало на оси Ot
    private int _otStart;
    //конец на оси Ot
    private int _otEnd;
    //резерв времени
    private int reserve;

    public int Start() {
        return start;
    }

    public int End() {
        return end;
    }

    public int Time() {
        return time;
    }

    public int Resource() {
        return resource;
    }

    public int OtStart() {
        return _otStart;
    }

    public void OtStart(int _otStart) {
        this._otStart = _otStart;
    }

    public int OtEnd() {
        return _otEnd;
    }

    public void OtEnd(int _otEnd) {
        this._otEnd = _otEnd;
    }
    
    public int Reserve(){
        return reserve;
    }
    
    public void Reserve(int reserve){
        this.reserve=reserve;
    }

    public Arc(int start, int end, int time, int resource) {
        this.start = start;
        this.end = end;
        this.time = time;
        this.resource = resource;
    }

}
