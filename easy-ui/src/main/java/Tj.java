import java.util.HashMap;

public class Tj {
    public static void main(String[] arg0){
        String a="sjd  ASDF  中文字符";
        Integer alength=a.length();
        char[] chars = a.toCharArray();
        HashMap<String, Integer> map = new HashMap<>();
        int count1=0;
        int count2=0;
        int count3=0;
        int count4=0;
        for(int i=0;i<alength;i++){
            boolean b = Character.isAlphabetic(chars[i]);
            if (b){
                count1=count1+1;
            }
            boolean c = Character.isDigit(chars[i]);
            if (c){
                count2=count2+1;
            }
            boolean d = Character.isSpaceChar(chars[i]);
            if (d){
                count3=count3+1;
            }
            if(chars[i]>='a'&&chars[i]<='z'||(chars[i]>='A'&&chars[i]<='Z')){
                count4++;
            }
        }
        map.put("字符数 ",count1);
        map.put("数字 ",count2);
        map.put("空格数 ",count3);
        map.put("字母数",count4);
        System.out.println(map);
    }
}
