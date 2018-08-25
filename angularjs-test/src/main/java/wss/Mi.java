package wss;

public class Mi {

    public static void main(String[] arg0){
        mi2();

    };
    public static void mi2(){

        for (int index=0;index<3;index++){


            final int finalindex=index;
            new Thread(){
                public void run(){
                    System.out.println(finalindex);
                }

            }.start();
        }

    }
}
