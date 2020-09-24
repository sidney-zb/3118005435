package simple;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class Analysis {
    public static void main(String[] args) {
        Map<String,int[]> resultMap = new HashMap<>();
        List<String> filename=new ArrayList<String>();
       for(int i=0;i<args.length;i++) {
			
	        filename.add(args[i]);		
		}      
        
        File file1= new File(filename.get(0)); 
        File file2=new File(filename.get(1));
   
        String text1=replaceSpecialStr(txttest.txt2String(file1));
        String text2=replaceSpecialStr(txttest.txt2String(file2));
        
        System.out.println(text1+"\n\n");
        System.out.println(text2+"\n\n");
        //ͳ��
        statistics(resultMap, IKUtils.divideText(text1),1);
        statistics(resultMap, IKUtils.divideText(text2),0);
        //������
        final Calculation calculation = new Calculation();
        resultMap.forEach((k,v)->{
            int[] arr = resultMap.get(k);
            calculation.setNumerator( arr[0] * arr[1]);
            calculation.setElementA(arr[0] * arr[0]);
            calculation.setElementB(arr[1] * arr[1]);
        });
        String result = String .format("%.2f",calculation.result());
        double a=Double.parseDouble(result);
       System.out.println("文本的相似度:" + a);
       writeToFile(filename.get(2),a);
       
    }
    //����
    public static String replaceSpecialStr(String str) {
        String repl = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }
 
    private static void statistics(Map<String,int[]> map,List<String> words ,int direction){
        if(null == words || words.size() == 0){
            return ;
        }
        int[] in = null;
        boolean flag = direction(direction);
        for (String word : words){
            int[] wordD = map.get(word);
            if(null == wordD){
                if(flag){
                    in = new int[]{1,0};
                }else {
                    in = new int[]{0,1};
                }
                map.put(word,in);
            }else{
                if(flag){
                    wordD[0]++;
                }else{
                    wordD[1]++;
                }
            }
        }
    }
    
    //�жϲ�ͬ����
    private static boolean direction(int direction){
        return direction == 1?true:false;
    }
    //д������
    public static void writeToFile(String pathname,double result ) {
        try {//�����׽�
            FileOutputStream fout=new FileOutputStream(pathname);
            DataOutputStream dout=new DataOutputStream(fout);
          
            String line = "";
            
            line="文本的相似度: "+String.valueOf(result);
            
            System.out.println("input:"+line);
            try {
                File writeName = new File(pathname); // ���·�������û����Ҫ����һ���µ�output.txt�ļ�
                writeName.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    out.write(line);
                    out.flush(); // �ѻ���������ѹ���ļ�
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            fout.close();
            dout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   }   
 


 class IKUtils {

    public static List<String> divideText(String text){
        if(null == text || "".equals(text.trim())){
            return null;
        }
        List<String> resultList = new ArrayList<>();
        StringReader re = new StringReader(text);
        IKSegmenter ik = new IKSegmenter(re, true);
        Lexeme lex = null;
        try {
            while ((lex = ik.next()) != null) {
                resultList.add(lex.getLexemeText());
            }
        } catch (Exception e) {
            //TODO
        }
        return resultList;
    }
 
}


 class Calculation{
	 
    private  double elementA=0;
    private  double elementB=0;
    private  double numerator=0;
    
    public void setElementA(double sum) {
    	
    	elementA=sum+elementA;
    }
 public void setElementB(double sum) {
    	
    	elementB=sum+elementB;
    }
 
 public void setNumerator(double sum) {
 	
	 numerator=sum+numerator;
 }
    
    public double result(){
        return numerator / Math.sqrt(elementA * elementB);
    }
 }
 class txttest {
     public static String txt2String(File file){
         String result = "";
         try{
             BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
             String s = null;
             while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
                 result = result + "\n" +s;
             }
             br.close();    
         }catch(Exception e){
             e.printStackTrace();
         }
         return result;
     }
 }