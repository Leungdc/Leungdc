package asmd;

public class CheckOut{
     StringBuilder checkOut(String a,String b,String operator) {
    	 int aNum=0;
    	 int bNum=0;
    	 StringBuilder SB=new StringBuilder();
    	 //判断a,b分别是什么类型的数字
    	 int[] ajudge=judgeNum(a);
    	 int[] bjudge=judgeNum(b);
    	  	//比较a,b的大小
    	 if(ajudge[0]*bjudge[1]>bjudge[0]*ajudge[1]) {
    		 //存进StringBuilder的顺序
    		 SB.append(operator).append(a).append(b);
    	 }else {
    		 SB.append(operator).append(b).append(a);
    	 }
    	return SB;	
    		
     }
     
 private int[] judgeNum(String str) {
    	 int position1=-1;
    	 int position2=-1;
    	 int[] Array = new int[3];//存分子分母
    	 
    	
    	 
    	 position1 = str.indexOf("/");
    	 position2 = str.indexOf("'");
    	 if(position1<0&&position2<0){//整数
    		 Array[0]=Integer.valueOf(str);
    		 Array[1]=1;
    	 }
    	 
         if((position1>0&&position2>0)||(position1>0&&position2<0)){//带分数或分数
        	 String str1[]=str.split("\\'|\\/");
        	int[] sons = new int[str.length()];
        	 for(int i=0;i<str1.length;i++) {
       		  sons[i]=Integer.parseInt(str1[i]);                 
                }
        	if(str1.length==3) {
        		Array[0]=sons[0]*sons[3]+sons[2];
        		Array[1]=sons[3];
        	}else {           		  
        		Array[0]=sons[0];
        		Array[1]=sons[1];
        	}
         }
		return Array;    	 
     } 
     
     public static void main(String args[]) {
    	 String a="5";
    	 String b="9";
    	 String operator="+";
    	 CheckOut check=new CheckOut();
    	 StringBuilder sb=new StringBuilder(check.checkOut(a, b, operator));
    	 System.out.println(sb.toString());
//    	 String str1[]=str.split("\\'|\\/");
//    	 for(int i=0;i<str1.length;i++) {
//    		 System.out.println(str1[i]);
//    	 }
     }
}
