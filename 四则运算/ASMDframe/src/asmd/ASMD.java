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
    private  final String[] OPERATOR= {"+","-","*","��"};
    StringBuilder Sb=new StringBuilder();//���ڲ���
    StringBuilder checkrightandwrong = new StringBuilder();
    ArrayList list=new ArrayList();//�洢���غ�ʽ�ӵĴ�
    StringBuilder problemTemp=new StringBuilder();//�������ɵ�ʽ��
	int operatorNum=4;//���������� 
	int operdataNum[]=new int[operatorNum+1];//����������
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
			//createfile("��ʽ�ʹ�","problem");
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
		this.textArea.setText("����ð�ź�����������Ŀ��Ŀ(����0������)��"+"\n"+"����ð�ź�������ֵ��Χ(����1������)��"+"\n"+"Ȼ������ʼ����ʽ�Ӱ�ť"+"\n"+"���ɵ���ʽ�ļ��ʹ��ļ���D�̸�Ŀ¼��");
		
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
	 * ���������ļ������ļ�
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
	 * �õ���Ŀ��Ŀ���ƺ����ַ�Χ�ķ���
	 */
	private void getRangeandNum(){
		String all = null;
		int position1 = 0, position2 = 0, position3 = 0;
		int num=0,range=0;
		all = this.textArea.getText();
		position1 = all.indexOf("\n");
		num = Integer.parseInt(all.substring(all.indexOf("��")+1,position1));
		position2 = all.indexOf("��", position1);
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
		int[] operator=GenerateOpertor(operatorNum,rand); //�������operatorNum���ַ�
		String equation=GenerateEquation(operator,operdataNum,range);	   
	    Calculator answer=new Calculator();
		list=answer.algorithm(equation);        
		 if(!list.isEmpty()) {
			 String STR=Sb.toString();
			 if(STR.indexOf(list.get(1).toString())==-1) {
				 Sb.append(list.get(1)).append(" "); 			
				 problemTemp.append("��").append(Integer.toString(Count+1)).append("�⣺").append(equation).append("\n");		 
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
	 * �������operatorNum���ַ���������һ�����鴢��(�±�����)
	 */	
	private  int[] GenerateOpertor(int operatorNum, Random rand) {	
		int[] operator=new int[operatorNum];
		for(int i=0;i<operatorNum;i++) {
			operator[i]=rand.nextInt(4);
		}
		return operator;	
	}
	
	/*
	 * ��������ɵĲ�������������������ʽ
	 * 
	 * ���ղ������ĸ���ȥѡҪ����ʽ�ӵ�����
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
	 * �жϴ𰸶Դ��붨λ����
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
		end=str.indexOf("��");
		for(int i=0;i<answer.length;i++){
			if(i==answer.length-1) {
				offset = str.indexOf("=", end+1);
				inputanswer[i]=str.substring(offset);
			}
			else {
			offset=str.indexOf("=",offset);
			end=str.indexOf("��",end+1);
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
		printright.append(")").append("��").append(Integer.toString(rightNum)).append("��").append("\n");
		printwrong.append(")").append("��").append(Integer.toString(wrongNum)).append("��").append("\n");
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
	 * ����������㷨
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
           //�������ɷ���
   		numerator = 1+all.nextInt(limit);
   		if(limit==2){
   			denominator = 2;
   		}
   		else{
   			denominator = 2+all.nextInt(limit-1);
   		}
   		int n = numerator, m = denominator;
   		if(n%m==0) {			//������ɵķ��ӷ�ĸ���淶
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
			if(j==1){			      		//�ж����Լ���Ƿ����1��
				if(carrier!=0){    			//�жϸ÷����ǲ��Ǽٷ������Ǿ�ת�ɴ�������ʽ	  
					n=(n%m);
					temp.append(carrier);
					temp.append("'");
					}
				temp.append(n);
				temp.append("/");
				temp.append(m);
				return temp.toString();
			}
			else{					//�жϸ÷����ǲ��Ǽٷ������Ǿ�ת�ɴ�������ʽ
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
	 * ŷ������ж��Ƿ��й�Լ��
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
	 * �����ı�������
	 */
	private void settext(){
		
	}
	
	/**
	 * 
	 * �����¼�
	 */
	private void EmptytextArea(){
		textArea.setText("");
	}
}
