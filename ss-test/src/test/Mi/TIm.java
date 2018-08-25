package Mi;

import org.junit.Test;

import java.util.Arrays;

public class TIm {

    @Test
    public void mit(){

        int[] a1 ={3,9,0,7,8};
        for (int i = 0; i < a1.length; i++) {
            for (int x=0;x<a1.length-1-i;x++){
                if(a1[i]>a1[i+1]){
                    int temp=a1[i];
                    a1[i]=a1[i+1];
                    a1[i+1]=temp;
                }
            }
        }
        System.out.println(Arrays.toString(a1));
    }
}
