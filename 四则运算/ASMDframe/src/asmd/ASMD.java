package asmd;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.TextArea;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;

public class ASMD extends JFrame {
	int problemNum=0;
	String[] answerTemp;
    private  final String[] OPERATOR= {"+","-","*","÷"};
    StringBuilder Sb=new StringBuilder();//用于查重
    StringBuilder checkrightandwrong = new StringBuilder();
    ArrayList list=new ArrayList();//存储查重和式子的答案
    StringBuilder problemTemp=new StringBuilder();//缓存生成的式子
	int operatorNum=4;//操作符个数 
	int operdataNum[]=new int[operatorNum+1];//操作数个数
	int Count=0;

	private JPanel contentPane;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ASMD frame = new ASMD();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ASMD() {
		initialize();
		try {
			//createfile("算式和答案","problem");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 561, 691);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 10, 287, 635);
		contentPane.add(scrollPane);
		
		this.textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		this.textArea.setText("请在冒号后输入生成题目数目(大于0的整数)："+"\n"+"请在冒号后输入数值范围(大于1的整数)："+"\n"+"然后点击开始生成式子按钮"+"\n"+"生成的算式文件和答案文件在D盘根目录下");
		
		JButton button = new JButton("\u751F\u6210\u7B97\u5F0F");
		button.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				getRangeandNum();
			}
		});
		button.setBounds(349, 96, 173, 23);
		contentPane.add(button);
		
		JButton btndaan = new JButton("\u63D0\u4EA4\u7B54\u6848");
		btndaan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAnswers(answerTemp);
			}
		});
		btndaan.setBounds(349, 151, 173, 23);
		contentPane.add(btndaan);
		
		JButton button_1 = new JButton("\u6E05\u7A7A\u5185\u5BB9");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmptytextArea();
				
			}
		});
		button_1.setBounds(349, 207, 173, 23);
		contentPane.add(button_1);
		
		
	}

	/**
	 * 创建问题文件，答案文件
	 */
	private void createfile(String data,String filename) {
		String sp = "\\";
		StringBuilder thefilename = new StringBuilder();
		thefilename.append("D:").append(sp).append(filename).append(".txt");
		File file = new File(thefilename.toString());
		try {
			if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
          //  fileWriter.write("");
            fileWriter.write(data);
            fileWriter.flush();
            fileWriter.close();
		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 得到题目数目限制和数字范围的方法
	 */
	private void getRangeandNum(){
		String all = null;
		int position1 = 0, position2 = 0, position3 = 0;
		int num=0,range=0;
		all = this.textArea.getText();
		position1 = all.indexOf("\n");
		num = Integer.parseInt(all.substring(all.indexOf("：")+1,position1));
		position2 = all.indexOf("：", position1);
		position3 = all.indexOf("\n", position2);
		range =  Integer.parseInt(all.substring(position2+1,position3));
		//textArea.setText(num+"\n"+range);
		this.answerTemp=new String[num];
		CreatProblems(num,range);
		
	}
	
	 private  void CreatProblems(int num,int range) {	
		 StringBuilder answerinfile = new StringBuilder();
		Random rand=new Random();	
		while(Count<num) {		
		int[] operator=GenerateOpertor(operatorNum,rand); //随机生成operatorNum个字符
		String equation=GenerateEquation(operator,operdataNum,range);	   
	    Calculator answer=new Calculator();
		list=answer.algorithm(equation);        
		 if(!list.isEmpty()) {
			 String STR=Sb.toString();
			 if(STR.indexOf(list.get(1).toString())==-1) {
				 Sb.append(list.get(1)).append(" "); 			
				 problemTemp.append("第").append(Integer.toString(Count+1)).append("题：").append(equation).append("\n");		 
				 answerTemp[Count]=list.get(0).toString();					 
				//  System.out.println(answerTemp[Count]); 
				 Count++;
			 }
		 }else{
			 CreatProblems(num-Count,range);	
		 }				 
	   }
	this.textArea.setText(problemTemp.toString());
	for(int i = 0;i<answerTemp.length;i++){
		answerinfile.append(Integer.toString(i+1)).append(".").append(answerTemp[i]).append("\n");
	}
	createfile(this.checkrightandwrong.toString(),"Grade");
	createfile(answerinfile.toString(),"Answer");
	createfile(problemTemp.toString(),"Problem");
	}
	
	
	
	/*
	 * 随机生成operatorNum个字符，并生成一个数组储存(下标数组)
	 */	
	private  int[] GenerateOpertor(int operatorNum, Random rand) {	
		int[] operator=new int[operatorNum];
		for(int i=0;i<operatorNum;i++) {
			operator[i]=rand.nextInt(4);
		}
		return operator;	
	}
	
	/*
	 * 把随机生成的操作符，操作数生成算式
	 * 
	 * 按照操做符的个数去选要生成式子的类型
	 */
	private  String GenerateEquation(int[] operator, int[] operdataNum, int range) {
		StringBuilder equation=new StringBuilder();
		int caseStyle=new Random().nextInt(13);
		switch(caseStyle) {
		case 0:
			//1+2
			equation.append( getRandomNum(range))
			        .append(" ")
			        .append(OPERATOR[operator[0]])
			        .append(" ")
			        .append(getRandomNum(range))
			        .append(" ")
			        .append("=");
			break;
		case 1:
			//1+2+3
			equation.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
			.append("=");
			break;
		case 2:
			//(1+2)+3
			equation.append("(")
			.append(" ")
			.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(")")
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
			.append("=");
			break;
		case 3:
			//1+(2+3)
			equation.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	        .append("(")
	        .append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
			.append(")")
	        .append(" ")
			.append("=");
			break;
		case 4:
			//1+2+3+4
			equation
			.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
		    .append(OPERATOR[operator[2]])
		    .append(" ")
		    .append(getRandomNum(range))
		    .append(" ")
			.append("=");
			break;
		case 5:
			//(1+2)+3+4
			equation.append("(")
			.append(" ")
			.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(")")
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
		    .append(OPERATOR[operator[2]])
		    .append(" ")
		    .append(getRandomNum(range))
		    .append(" ")
			.append("=");
			break;
		case 6:
			//1+(2+3)+4
			equation
			.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	        .append("(")
			.append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
			.append(")")
	        .append(" ")
		    .append(OPERATOR[operator[2]])
		    .append(" ")
		    .append(getRandomNum(range))
		    .append(" ")
			.append("=");
			break;
		case 7:
			//1+2+(3+4)
			equation
			.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append("(")
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
		    .append(OPERATOR[operator[2]])
		    .append(" ")
		    .append(getRandomNum(range))
		    .append(" ")
		    .append(")")
	        .append(" ")
			.append("=");
			break;
		case 8:
			//(1+2)+(3+4)
			equation.append("(")
			.append(" ")
			.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(")")
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append("(")
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
		    .append(OPERATOR[operator[2]])
		    .append(" ")
		    .append(getRandomNum(range))
		    .append(" ")
		    .append(")")
	        .append(" ")
			.append("=");
			break;
		case 9:
			//((1+2)+3)+4
			equation
			.append("(")
			.append(" ")
			.append("(")
			.append(" ")
			.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(")")
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
			.append(")")
		    .append(" ")
		    .append(OPERATOR[operator[2]])
		    .append(" ")
		    .append(getRandomNum(range))
		    .append(" ")
			.append("=");
			break;
		case 10:
			//(1+(2+3))+4
			equation
			.append("(")
			.append(" ")
			.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	        .append("(")
			.append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
			.append(")")
	        .append(" ")
	        .append(")")
	        .append(" ")
		    .append(OPERATOR[operator[2]])
		    .append(" ")
		    .append(getRandomNum(range))
		    .append(" ")
			.append("=");
			break;
		case 11:
			//1+((2+3)+4)
			equation
			.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	        .append("(")
			.append(" ")
	        .append("(")
			.append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
			.append(")")
	        .append(" ")
		    .append(OPERATOR[operator[2]])
		    .append(" ")
		    .append(getRandomNum(range))
		    .append(" ")
		    .append(")")
	        .append(" ")
			.append("=");
			break;
		case 12:
			//1+(2+(3+4))
			equation
			.append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[0]])
	        .append(" ")
	    	.append("(")
			.append(" ")
	        .append(getRandomNum(range))
	        .append(" ")
	        .append(OPERATOR[operator[1]])
			.append(" ")
			.append("(")
			.append(" ")
			.append(getRandomNum(range))
			.append(" ")
		    .append(OPERATOR[operator[2]])
		    .append(" ")
		    .append(getRandomNum(range))
		    .append(" ")
		    .append(")")
	        .append(" ")
	        .append(")")
	        .append(" ")
			.append("=");
		}
		return equation.toString();
	
	}
	
	/**
	 * 判断答案对错与定位错题
	 */
	private void checkAnswers(String answer[]){
		String str = new String();
		String inputanswer[] = new String[answer.length];
		int rightNum = 0, wrongNum = 0;
		int offset=0, end=0;
		str=this.textArea.getText();
		StringBuilder printright = new StringBuilder();
		StringBuilder printwrong = new StringBuilder();
		printright.append("Right(");
		printwrong.append("Wrong(");
		end=str.indexOf("第");
		for(int i=0;i<answer.length;i++){
			if(i==answer.length-1) {
				offset = str.indexOf("=", end+1);
				inputanswer[i]=str.substring(offset);
			}
			else {
			offset=str.indexOf("=",offset);
			end=str.indexOf("第",end+1);
			inputanswer[i]=str.substring(offset+1,end).replace("\n", "");
			}
			offset++;
			end++;
			if(inputanswer[i].equals(answer[i])) {
				printright.append(Integer.toString(i+1)).append(",");
				rightNum++;
			}
			else {
				printwrong.append(Integer.toString(i+1)).append(",");
				wrongNum++;
			}
			
		}
		printright.append(")").append("共").append(Integer.toString(rightNum)).append("题").append("\n");
		printwrong.append(")").append("共").append(Integer.toString(wrongNum)).append("题").append("\n");
		checkrightandwrong.append(printright.toString()).append(printwrong.toString());
		this.textArea.setText(printright.toString()+"\n"+printwrong.toString());
	/*	try {
			FileOutputStream fs = new FileOutputStream("D:"+separator+"answer.txt");			
			DataOutputStream ds = new DataOutputStream(fs);
			ds.writeUTF(printright.toString());
			ds.writeUTF(printwrong.toString());
			ds.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	/**
	 * 生成随机数算法
	 */
	
	private String getRandomNum(int limit){
   	Random all = new Random();
   	StringBuilder temp = new StringBuilder();
   	int numtype = all.nextInt(2);
   	int num = 0, carrier=0, numerator = 0, denominator = 0;
   	int j = 0;
   	if(numtype==0){
   		num=0+all.nextInt(limit+1);
   		return Integer.toString(num);
   	}
   	else{
           //此行生成分数
   		numerator = 1+all.nextInt(limit);
   		if(limit==2){
   			denominator = 2;
   		}
   		else{
   			denominator = 2+all.nextInt(limit-1);
   		}
   		int n = numerator, m = denominator;
   		if(n%m==0) {			//如果生成的分子分母不规范
			for(int i= 0 ; i<=100; i++){
				n = 1+all.nextInt(limit);
				m = 2+all.nextInt(limit-1);
				if(n%m!=0) break;
			}
		}
			if(m>n)   j = Euclid(m, n);
			else{
				 carrier = n/m;
				 j = Euclid(n,m);		
			}
			if(j==1){			      		//判断最大公约数是否等于1；
				if(carrier!=0){    			//判断该分数是不是假分数，是就转成带分数形式	  
					n=(n%m);
					temp.append(carrier);
					temp.append("'");
					}
				temp.append(n);
				temp.append("/");
				temp.append(m);
				return temp.toString();
			}
			else{					//判断该分数是不是假分数，是就转成带分数形式
				n/=j;
				m/=j;
				if(carrier!=0){  
					n=(n%m);
					temp.append(carrier);
					temp.append("'");
				}
				temp.append(n);
				temp.append("/");
				temp.append(m);
				return temp.toString();	    	
			}

   	}
}
	
	/**
	 * 欧几里得判断是否有公约数
	 */
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

	/**
	 * 设置文本域内容
	 */
	private void settext(){
		
	}
	
	/**
	 * 
	 * 重置事件
	 */
	private void EmptytextArea(){
		textArea.setText("");
	}
}
