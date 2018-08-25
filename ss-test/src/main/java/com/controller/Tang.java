package com.controller;

import java.util.LinkedList;

public class Tang {

  public static void main(String[] arg0){
      LinkedList<String> list = new LinkedList<>();
      list.add("一");
      list.add("mo");
      list.add("sheng");
      list.add("四");

      for (String o : list) {
          System.out.println(o);
      }

  };

}