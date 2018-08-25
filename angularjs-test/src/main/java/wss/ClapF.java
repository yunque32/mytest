package wss;

public class ClapF {

    private String pf="mimimi";
    private static String sf="iiii:";
    {
        System.out.println("父类的代码块"+pf);
    }
    static {
        System.out.println("父类的静态代码块"+sf);
    }
    public ClapF(){
        System.out.println("父类的空参构造");
    }

}
