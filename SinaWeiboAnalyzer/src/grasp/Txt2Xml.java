/*
 *����ר���࣬txt�ļ�ת��Ϊxml�ļ�
 */
package grasp;

import help.WeiboConstants;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;



public class Txt2Xml {


	//���xml�ļ�
	public static String outputXml(List<String> content,List<String> mark,String path){
		String news = null;			
	//	List<String> content = txt2xml.readFile("D:\\a.txt");
	//	List<String> mark = txt2xml.readMark("D:\\b.txt");

		System.out.println(content.size());
		System.out.println(mark.size());
		if(mark.size()==content.size()){
			Element root = new Element("WeiboList");			
		for(int i=0;i<content.size();i++){
			Element elements = new Element("΢��");
			elements.addContent(new Element("����").setText(content.get(i)));			
			elements.addContent(new Element("��ע").setText(mark.get(i)));
			root.addContent(elements);
		}		
		Document Doc = new Document(root);
		Format format = Format.getPrettyFormat();
		XMLOutputter XMLOut = new XMLOutputter(format);
		try {
			XMLOut.output(Doc, new FileOutputStream(path+"Game-Weibo.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  news = "���XML�ɹ�������"+mark.size()+"��΢����";
///		 System.out.println("���XML�ɹ���");
		
		}else{	
			news = "΢�����ݺͱ�ע������һ�£�";
///			System.out.println("΢�����ݺͱ�ע������һ�£�");
			}
		return news; //������Ϣ��		
	}
	//��ȡtxt�е�΢������
	public static List<String> readFile(String filename){
		  List<String> filecon = new ArrayList<String>();
		  String line ;
		  BufferedReader file = null;
		  try{
		   file = new BufferedReader(new FileReader(filename));
		   while ((line = file.readLine()) != null) {
		    if (!line.equals("")) // ����Ҫ��ȡ����
		    {
		     filecon.add(line);
	//	     System.out.println(line);
		    }
		   }
		   file.close();
		  }catch(IOException e){
		   e.printStackTrace();
		  }		   
		  return filecon;
		 }
	//��ȡtxt�еı�ע
	public static List<String> readMark(String filename){
		  List<String> filecon = new ArrayList<String>();
		  String line ;
		  BufferedReader file = null;
		  try{
		   file = new BufferedReader(new FileReader(filename));
		   while ((line = file.readLine()) != null) {
		    if (!line.equals("")) // ����Ҫ��ȡ����
		    {
		    	if(line.equals(WeiboConstants.WORD_FOR_POSITIVE)
		    			||line.equals(WeiboConstants.WORD_FOR_NEUTRAL)
		    			||line.equals(WeiboConstants.WORD_FOR_NEGATIVE))//�жϱ�ע
		    	{
		     filecon.add(line);
		   //  System.out.println(line);	
		    }
		    }
		   }
		   file.close();
		  }catch(IOException e){
		   e.printStackTrace();
		  }		   
		  return filecon;
		 }
	
	
}
