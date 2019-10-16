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
		  LinkedList<String> numList=new LinkedList<>();//存放数字
    	  Stack<String> operatorStack=new Stack<>();//放操作符
    	  HashMap<String,Integer> hashMap=new HashMap<>();//存放字符优先级
    	  hashMap.put("(", 0);
          hashMap.put("+", 1);
          hashMap.put("-", 1);
          hashMap.put("*", 2);
          hashMap.put("÷", 2); 
          ArrayList list=new ArrayList();
          
          CheckOut check=new CheckOut();//生成运算顺序，用于查重 
          StringBuilder ba = new StringBuilder();
          
          String str1[]=str.split("\\ ");
//            for(String string:str1)
//    	            System.out.println(string);
            
            
      	    int leftBrankets = 0;//用于检测‘（’的个数
      	    int operatorCount=0;
      	    
          for(int i=0;i<str1.length;i++) {
        	 
        	 
        	  StringBuilder digit=new StringBuilder();
              if(!"".equals(str1[i])) {
            	 
            	  //判断是否是数字
            	  char num[]=str1[i].toCharArray();
            	  if(Character.isDigit(num[0])) {
            		  
//            	     System.out.println(str1[i]);
            	     
            	     numList.addLast(String.valueOf(str1[i]));//压进数字栈
            	     continue;//结束本次循环，回到for语句
            	    	 }
            	  
            	  //不是数字，是符号
            	  else {
            		 
            		  char operatorOfChar=str1[i].charAt(0);
            		  
//            		  System.out.println(operatorOfChar+"符号");
            		  
            		  switch(operatorOfChar) {
            		  case '(':{
            			  leftBrankets++;
            			  break;
            		  }
            		  
            		  case ')':{
            			  String stmp;//取符号栈元素，考虑到(1+2*3)+4这种情况，要比较操作符的优先级
            			  String stmd;
            			  if(!operatorStack.isEmpty()) {
            				  
            				  if(operatorCount==2&&leftBrankets==1) {
            					//取出符号栈里的操作符（两个）
                				  stmp=operatorStack.pop();
                				  stmd=operatorStack.pop();
                				  if(hashMap.get(stmp)>hashMap.get(stmd)) {
                					  String a=numList.removeLast();
                					  String b=numList.removeLast();
                					  String result=calculate(b,a,stmp);
                					 
                					  if(result.contentEquals("出现了负值"))
                						  return list ;
                					  ba.append(check.checkOut(b, a, stmp));
                					
                					  
                					  numList.push(result);//将结果压入栈
                					  operatorStack.push(stmd);//将未进行计算的操作符压回符号栈
                					  stmp = operatorStack.pop(); //符号指向下一个计算符号,再进行一次运算
                					  String c=numList.removeLast();
                					  String d=numList.removeLast();
                					  String result02=calculate(d,c,stmp);
                					  if(result02.contentEquals("出现了负值"))
                						  return list ;
                					  ba.append(check.checkOut(d, c, stmp));
            
                					  
                					  numList.push(result02);//将结果压入栈
                					  
                				  }else {
                					  String a=numList.removeFirst();
                					  String b=numList.removeFirst();
                					  String result=calculate(a,b,stmd);
                					  if(result.contentEquals("出现了负值"))
                						  return list ;
                					  ba.append(check.checkOut(a, b, stmd));
                					
                					  
                					  numList.addLast(result);
                					  operatorStack.push(stmp);
                					  stmp = operatorStack.pop(); //符号指向下一个计算符号
                					  String c=numList.removeLast();
                					  String d=numList.removeLast();
                					  String result02=calculate(d,c,stmp);
                					  if(result02.contentEquals("出现了负值"))
                						  return list ;
                					  ba.append(check.checkOut(d, c, stmp));
                					
                					  
                					  numList.push(result02);//将结果压入栈
                				  }
            				  }else if(leftBrankets==2||(operatorCount==1&&leftBrankets==1)){
            					  stmp=operatorStack.pop();
                				  String a=numList.removeLast();
            					  String b=numList.removeLast();
            					  String result=calculate(b,a,stmp);
            					  if(result.contentEquals("出现了负值"))
            						  return list ;
            					  ba.append(check.checkOut(b, a, stmp));
            					
            					  numList.addLast(result);
            					  /*判定下一个str[i]是什么*/
            					  
            				  }
            				  break;  
            			  }
            		  }
            		  
                      
                      case '=':{
                    	  String stmp;
                    	  while (!operatorStack.isEmpty()) { //当前符号栈里面还有+ - * /,即还没有算完
                              stmp = operatorStack.pop();
                              String a = numList.removeLast();
                              String b = numList.removeLast();
                              String result = calculate(b, a, stmp);
                              if(result.contentEquals("出现了负值"))
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
                    	 while (!operatorStack.isEmpty()&&leftBrankets==0) { //如果符号栈有符号
                             stmp = operatorStack.pop(); //当前符号栈，栈顶元素
                             if (hashMap.get(stmp) >= hashMap.get(String.valueOf(operatorOfChar))) { //比较优先级
                                 String a = numList.removeLast();
                                 String b = numList.removeLast();
                                 String result =calculate (b, a, stmp);
                                 if(result.contentEquals("出现了负值"))
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
   	 * 计算结果
   	 */	
   private String calculate(String s1, String s2, String processor){
   		int theinteger=0, theN=0, theM = 0;
   		int  j = 1;   //放最大公约数
   		String num1 = null, num2 =null;
   		StringBuilder temp = new StringBuilder();
   		int Nfornum1 = 0, Mfornum1 = 0, Nfornum2 = 0, Mfornum2 = 0;  
   		int carrier1 = 0, carrier2 = 0;
   		num1 = s1.substring(0);
   		num2 = s2.substring(0);
   		if(processor.equals("+")){
   			Mfornum1 = gettheM(num1);		//确定加号前面的数字的分母值(此处要保证该数字的最后一位元素是空格)
   			Nfornum1 = gettheN(num1, Mfornum1);	//确定加号后面的数字的分子值(此处要保证该数字的0号元素为数字，最后一位是空格（整数情况）)
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
   				if(carrier1!=0){    			//判断该分数是不是假分数，是就转成带分数形式	  
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
   				Mfornum1 = gettheM(num1);		//确定加号前面的数字的分母值(此处要保证该数字的最后一位元素是空格)
   				Nfornum1 = gettheN(num1, Mfornum1);	//确定加号后面的数字的分子值(此处要保证该数字的0号元素为数字，最后一位是空格（整数情况）)
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
   						if(carrier1!=0){    			//判断该分数是不是假分数，是就转成带分数形式	  
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
   					temp.append("出现了负值");
   					
   					}
   		return temp.toString();
   	}
   		
   		else if (processor.equals("*")){
   			Mfornum1 = gettheM(num1);		//确定加号前面的数字的分母值(此处要保证该数字的最后一位元素是空格)
   			Nfornum1 = gettheN(num1, Mfornum1);	//确定加号后面的数字的分子值(此处要保证该数字的0号元素为数字，最后一位是空格（整数情况）)
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
   				if(carrier1!=0){    			//判断该分数是不是假分数，是就转成带分数形式	  
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
   		else if (processor.equals("÷")){
   			Mfornum1 = gettheM(num1);		//确定加号前面的数字的分母值(此处要保证该数字的最后一位元素是空格)
   			Nfornum1 = gettheN(num1, Mfornum1);	//确定加号后面的数字的分子值(此处要保证该数字的0号元素为数字，最后一位是空格（整数情况）)
   			Mfornum2 = gettheM(num2);		//"2'4/21 "
   			Nfornum2 = gettheN(num2, Mfornum2);	//" 89"
   			theN = Nfornum1*Mfornum2;
   			theM = Mfornum1*Nfornum2;
   			if(Nfornum2==0)  temp.append("出现了负值");
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
   				if(carrier1!=0){    			//判断该分数是不是假分数，是就转成带分数形式	  
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
   		else return "运算符号没有规范传入";
   	}
   	/**
   	 * 算结果的分子分母方法
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
    	   String str=("12 ÷ ( 11 - 14 ) =");
    	   test01.algorithm(str);
    	   
    	   
       }
}
