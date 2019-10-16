package asmd;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator{
       public ArrayList algorithm(String str) { 
		  LinkedList<String> numList=new LinkedList<>();//�������
    	  Stack<String> operatorStack=new Stack<>();//�Ų�����
    	  HashMap<String,Integer> hashMap=new HashMap<>();//����ַ����ȼ�
    	  hashMap.put("(", 0);
          hashMap.put("+", 1);
          hashMap.put("-", 1);
          hashMap.put("*", 2);
          hashMap.put("��", 2); 
          ArrayList list=new ArrayList();
          
          CheckOut check=new CheckOut();//��������˳�����ڲ��� 
          StringBuilder ba = new StringBuilder();
          
          String str1[]=str.split("\\ ");
//            for(String string:str1)
//    	            System.out.println(string);
            
            
      	    int leftBrankets = 0;//���ڼ�⡮�����ĸ���
      	    int operatorCount=0;
      	    
          for(int i=0;i<str1.length;i++) {
        	 
        	 
        	  StringBuilder digit=new StringBuilder();
              if(!"".equals(str1[i])) {
            	 
            	  //�ж��Ƿ�������
            	  char num[]=str1[i].toCharArray();
            	  if(Character.isDigit(num[0])) {
            		  
//            	     System.out.println(str1[i]);
            	     
            	     numList.addLast(String.valueOf(str1[i]));//ѹ������ջ
            	     continue;//��������ѭ�����ص�for���
            	    	 }
            	  
            	  //�������֣��Ƿ���
            	  else {
            		 
            		  char operatorOfChar=str1[i].charAt(0);
            		  
//            		  System.out.println(operatorOfChar+"����");
            		  
            		  switch(operatorOfChar) {
            		  case '(':{
            			  leftBrankets++;
            			  break;
            		  }
            		  
            		  case ')':{
            			  String stmp;//ȡ����ջԪ�أ����ǵ�(1+2*3)+4���������Ҫ�Ƚϲ����������ȼ�
            			  String stmd;
            			  if(!operatorStack.isEmpty()) {
            				  
            				  if(operatorCount==2&&leftBrankets==1) {
            					//ȡ������ջ��Ĳ�������������
                				  stmp=operatorStack.pop();
                				  stmd=operatorStack.pop();
                				  if(hashMap.get(stmp)>hashMap.get(stmd)) {
                					  String a=numList.removeLast();
                					  String b=numList.removeLast();
                					  String result=calculate(b,a,stmp);
                					 
                					  if(result.contentEquals("�����˸�ֵ"))
                						  return list ;
                					  ba.append(check.checkOut(b, a, stmp));
                					
                					  
                					  numList.push(result);//�����ѹ��ջ
                					  operatorStack.push(stmd);//��δ���м���Ĳ�����ѹ�ط���ջ
                					  stmp = operatorStack.pop(); //����ָ����һ���������,�ٽ���һ������
                					  String c=numList.removeLast();
                					  String d=numList.removeLast();
                					  String result02=calculate(d,c,stmp);
                					  if(result02.contentEquals("�����˸�ֵ"))
                						  return list ;
                					  ba.append(check.checkOut(d, c, stmp));
            
                					  
                					  numList.push(result02);//�����ѹ��ջ
                					  
                				  }else {
                					  String a=numList.removeFirst();
                					  String b=numList.removeFirst();
                					  String result=calculate(a,b,stmd);
                					  if(result.contentEquals("�����˸�ֵ"))
                						  return list ;
                					  ba.append(check.checkOut(a, b, stmd));
                					
                					  
                					  numList.addLast(result);
                					  operatorStack.push(stmp);
                					  stmp = operatorStack.pop(); //����ָ����һ���������
                					  String c=numList.removeLast();
                					  String d=numList.removeLast();
                					  String result02=calculate(d,c,stmp);
                					  if(result02.contentEquals("�����˸�ֵ"))
                						  return list ;
                					  ba.append(check.checkOut(d, c, stmp));
                					
                					  
                					  numList.push(result02);//�����ѹ��ջ
                				  }
            				  }else if(leftBrankets==2||(operatorCount==1&&leftBrankets==1)){
            					  stmp=operatorStack.pop();
                				  String a=numList.removeLast();
            					  String b=numList.removeLast();
            					  String result=calculate(b,a,stmp);
            					  if(result.contentEquals("�����˸�ֵ"))
            						  return list ;
            					  ba.append(check.checkOut(b, a, stmp));
            					
            					  numList.addLast(result);
            					  /*�ж���һ��str[i]��ʲô*/
            					  
            				  }
            				  break;  
            			  }
            		  }
            		  
                      
                      case '=':{
                    	  String stmp;
                    	  while (!operatorStack.isEmpty()) { //��ǰ����ջ���滹��+ - * /,����û������
                              stmp = operatorStack.pop();
                              String a = numList.removeLast();
                              String b = numList.removeLast();
                              String result = calculate(b, a, stmp);
                              if(result.contentEquals("�����˸�ֵ"))
        						  return list ;
                              ba.append(check.checkOut(b, a, stmp));
//            					  System.out.println(ba.toString());
            					  
                              numList.addLast(result);
                          }
                          break;
                      }
                      
                      default:{
                    	  operatorCount++;
                    	String stmp;
                    	 while (!operatorStack.isEmpty()&&leftBrankets==0) { //�������ջ�з���
                             stmp = operatorStack.pop(); //��ǰ����ջ��ջ��Ԫ��
                             if (hashMap.get(stmp) >= hashMap.get(String.valueOf(operatorOfChar))) { //�Ƚ����ȼ�
                                 String a = numList.removeLast();
                                 String b = numList.removeLast();
                                 String result =calculate (b, a, stmp);
                                 if(result.contentEquals("�����˸�ֵ"))
           						  return list ;
                                 ba.append(check.checkOut(b, a, stmp));
                                 numList.addLast(result);
                             }else {
                            	 operatorStack.push(stmp);
                                 break;
                                }
                             }
                    	 operatorStack.push(String.valueOf(operatorOfChar));
                    	 break;
                         }
                      
            		  }
            		  
            	  }
            	     
              }
        	    	
          }
          list.add(numList.getLast());
          list.add(ba);
          System.out.println(list);
    	   return list; 
       }
       

//       }
   	/**
   	 * ������
   	 */	
   private String calculate(String s1, String s2, String processor){
   		int theinteger=0, theN=0, theM = 0;
   		int  j = 1;   //�����Լ��
   		String num1 = null, num2 =null;
   		StringBuilder temp = new StringBuilder();
   		int Nfornum1 = 0, Mfornum1 = 0, Nfornum2 = 0, Mfornum2 = 0;  
   		int carrier1 = 0, carrier2 = 0;
   		num1 = s1.substring(0);
   		num2 = s2.substring(0);
   		if(processor.equals("+")){
   			Mfornum1 = gettheM(num1);		//ȷ���Ӻ�ǰ������ֵķ�ĸֵ(�˴�Ҫ��֤�����ֵ����һλԪ���ǿո�)
   			Nfornum1 = gettheN(num1, Mfornum1);	//ȷ���Ӻź�������ֵķ���ֵ(�˴�Ҫ��֤�����ֵ�0��Ԫ��Ϊ���֣����һλ�ǿո����������)
   			Mfornum2 = gettheM(num2);		//"2'4/21 "
   			Nfornum2 = gettheN(num2, Mfornum2);	//" 89"
   			theN = Nfornum1*Mfornum2+Nfornum2*Mfornum1;
   			theM = Mfornum1*Mfornum2;
   			if(Nfornum1==0&&Nfornum2==0){
   				temp.append("0");
   				}
   			else{
   			if(theN>theM){
   				j=Euclid(theN,theM);
   				carrier1 = theN/theM;
   				}
   			else
   				j=Euclid(theM,theN);
   			theN/=j;
   			theM/=j;
   			if(theN%theM==0){
   				theN = theN/theM;
   				temp.append(Integer.toString(theN));
   			}
   			else{
   				if(carrier1!=0){    			//�жϸ÷����ǲ��Ǽٷ������Ǿ�ת�ɴ�������ʽ	  
   					theN=(theN%theM);
   					temp.append(carrier1);
   					temp.append("'");
   					}
   				temp.append(Integer.toString(theN));
   				temp.append("/");
   				temp.append(Integer.toString(theM));
   			}
   			}
   			return temp.toString();
   		}
   		else if (processor.equals("-")){
   				Mfornum1 = gettheM(num1);		//ȷ���Ӻ�ǰ������ֵķ�ĸֵ(�˴�Ҫ��֤�����ֵ����һλԪ���ǿո�)
   				Nfornum1 = gettheN(num1, Mfornum1);	//ȷ���Ӻź�������ֵķ���ֵ(�˴�Ҫ��֤�����ֵ�0��Ԫ��Ϊ���֣����һλ�ǿո����������)
   				Mfornum2 = gettheM(num2);		//"2'4/21 "
   				Nfornum2 = gettheN(num2, Mfornum2);	//" 89"
   				theN = Nfornum1*Mfornum2-Nfornum2*Mfornum1;
   				theM = Mfornum1*Mfornum2;
   				if(theN==0){
   					temp.append("0");
   					}
   				else if(theN>0){
   					if(theN>theM){
   						j=Euclid(theN,theM);
   						carrier1 = theN/theM;
   						}
   					else
   						j=Euclid(theM,theN);
   					theN/=j;
   					theM/=j;
   					if(theN%theM==0){
   						theN = theN/theM;
   						temp.append(Integer.toString(theN));					}
   					else{
   						if(carrier1!=0){    			//�жϸ÷����ǲ��Ǽٷ������Ǿ�ת�ɴ�������ʽ	  
   							theN=(theN%theM);
   							temp.append(carrier1);
   							temp.append("'");
   							}
   						temp.append(Integer.toString(theN));
   						temp.append("/");
   						temp.append(Integer.toString(theM));
   					}
   				}
   				else{
   					temp.append("�����˸�ֵ");
   					
   					}
   		return temp.toString();
   	}
   		
   		else if (processor.equals("*")){
   			Mfornum1 = gettheM(num1);		//ȷ���Ӻ�ǰ������ֵķ�ĸֵ(�˴�Ҫ��֤�����ֵ����һλԪ���ǿո�)
   			Nfornum1 = gettheN(num1, Mfornum1);	//ȷ���Ӻź�������ֵķ���ֵ(�˴�Ҫ��֤�����ֵ�0��Ԫ��Ϊ���֣����һλ�ǿո����������)
   			Mfornum2 = gettheM(num2);		//"2'4/21 "
   			Nfornum2 = gettheN(num2, Mfornum2);	//" 89"
   			theN = Nfornum1*Nfornum2;
   			theM = Mfornum1*Mfornum2;
   			if(theN==0){
   				temp.append("0");
   			}
   			else{
   			if(theN>theM){
   				j=Euclid(theN,theM);
   				carrier1 = theN/theM;
   				}
   			else
   				j=Euclid(theM,theN);
   			theN/=j;
   			theM/=j;
   			if(theN%theM==0){
   				theN = theN/theM;
   				temp.append(Integer.toString(theN));
   			}
   			else{
   				if(carrier1!=0){    			//�жϸ÷����ǲ��Ǽٷ������Ǿ�ת�ɴ�������ʽ	  
   					theN=(theN%theM);
   					temp.append(carrier1);
   					temp.append("'");
   					}
   				temp.append(Integer.toString(theN));
   				temp.append("/");
   				temp.append(Integer.toString(theM));
   			}
   			}
   			return temp.toString();
   		}
   		else if (processor.equals("��")){
   			Mfornum1 = gettheM(num1);		//ȷ���Ӻ�ǰ������ֵķ�ĸֵ(�˴�Ҫ��֤�����ֵ����һλԪ���ǿո�)
   			Nfornum1 = gettheN(num1, Mfornum1);	//ȷ���Ӻź�������ֵķ���ֵ(�˴�Ҫ��֤�����ֵ�0��Ԫ��Ϊ���֣����һλ�ǿո����������)
   			Mfornum2 = gettheM(num2);		//"2'4/21 "
   			Nfornum2 = gettheN(num2, Mfornum2);	//" 89"
   			theN = Nfornum1*Mfornum2;
   			theM = Mfornum1*Nfornum2;
   			if(Nfornum2==0)  temp.append("�����˸�ֵ");
   			else if (Nfornum1==0) temp.append("0");
   			else{
   			if(theN>theM){
   				j=Euclid(theN,theM);
   				carrier1 = theN/theM;
   				}
   			else		j=Euclid(theM,theN);
   			theN/=j;
   			theM/=j;
   			if(theN%theM==0){
   				theN = theN/theM;
   				temp.append(Integer.toString(theN));
   			}
   			else{
   				if(carrier1!=0){    			//�жϸ÷����ǲ��Ǽٷ������Ǿ�ת�ɴ�������ʽ	  
   					theN=(theN%theM);
   					temp.append(carrier1);
   					temp.append("'");
   					}
   				temp.append(Integer.toString(theN));
   				temp.append("/");
   				temp.append(Integer.toString(theM));
//   				temp.append("\n");
   			}
   			}
   		return temp.toString();
   		}
   		else return "�������û�й淶����";
   	}
   	/**
   	 * �����ķ��ӷ�ĸ����
   	 */
   	private int gettheN(String beforeM,int M){
   		int theN = 0;
   		int position1 = -1, position2 = -1;
   		position1 = beforeM.indexOf("/");
   		position2 = beforeM.indexOf("'");
   		if(position1<0&&position2<0){
   			try{
   				theN = theN + M * (Integer.parseInt(beforeM.substring(0)));
   			}  catch (NumberFormatException  e)    {
   				e.printStackTrace();
   			}
   			return theN;
   	}
   		else if(position1>0&&position2<0){		
   				try{
   					theN += Integer.parseInt(beforeM.substring(0,position1));
   				}  catch (NumberFormatException  e)    {
   					e.printStackTrace();
   				}
   				return theN;
   		}
   		else if(position1>0&&position2>0){
   			try{
   				theN = theN + M * (Integer.parseInt(beforeM.substring(0,position2)));
   			}  catch (NumberFormatException  e)    {
   				e.printStackTrace();
   			}
   			try{
   				theN += Integer.parseInt(beforeM.substring(position2+1,position1));
   			}  catch (NumberFormatException  e)    {
   				e.printStackTrace();
   			}
   			return theN;
   	}
   		else return -1;
   	}

   	private int gettheM(String afterN){
   		int theM = 0;
   		int position2  = -1;
   		position2 = afterN.indexOf("/");
   		int thezero = 0;
   		if(position2>0){
   				try{
   					theM = Integer.parseInt(afterN.substring(position2+1));
   				}  catch (NumberFormatException  e)    {
   					e.printStackTrace();
   				}
   		}
   		else {
   		theM=1;
   		}
   		return theM;
   	}       
     
   	private  int Euclid(int m,int n){
		while(n!=0){
			int r;
			r=m%n;
			m=n;
			n=r;
			Euclid(m,n);
			}
		return m;
		}
       
       public static void main(String args[]) {
    	   Calculator test01=new Calculator();
    	   String str=("12 �� ( 11 - 14 ) =");
    	   test01.algorithm(str);
    	   
    	   
       }
}
