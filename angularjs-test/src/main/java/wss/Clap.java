package wss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resources;

public class Clap extends ClapF{
    public static void main(String[] arg0){
        new Clap();
    };
    private String pf="pp:";
    private static String sf="ps:";
    {
        System.out.println("子类的代码块"+pf);
    }
    static {
            System.out.println("子类的静态代码块"+sf);
    }
    public Clap(){
        System.out.println("子类的空参构造");
    }



}
