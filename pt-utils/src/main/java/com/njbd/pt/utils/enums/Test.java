package com.njbd.pt.utils.enums;


/**
 * Created by 李建成
 *
 * @date 2016/12/9-13:38.on NJBD
 */
public class Test {
   static  void Print(Day [] dates){
        for (Day day:dates){
            System.out.println(day.ordinal()+":"+day);
        }
    }

    public static void main(String[] args) {
        Print(Day.values());
    }

}
