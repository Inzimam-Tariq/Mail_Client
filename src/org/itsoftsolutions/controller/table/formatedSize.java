/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.table;

import java.util.Comparator;

/**
 *
 * @author Inzimam
 */
public class formatedSize implements Comparator<formatedSize>{
    
    private int size;

    public formatedSize(int size) {
        this.size = size;
    }
    
    @Override
    public String toString(){
        StringBuilder returnVal = new StringBuilder();
        int unit = 1024;
        if (size <= 0) {
            returnVal.append(" 0");
        } else if (size < unit) {
            returnVal.append(size).append(" B");
        } else if (size < (Math.pow(unit, 2))) {
            returnVal.append(size / unit).append(" KB");
        } else {
            returnVal.append(size / (int) Math.pow(unit, 2)).append(" MB");
        }
        
        return returnVal.toString();
    }

    @Override
    public int compare(formatedSize val1, formatedSize val2) {
        Integer int1 = val1.size;
        Integer int2 = val2.size;
        return int1.compareTo(int2);
    }
    
}
