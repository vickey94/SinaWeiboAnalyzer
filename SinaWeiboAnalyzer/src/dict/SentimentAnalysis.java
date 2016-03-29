/*  ��з�����
  ����ץȡ��������΢����з�
  */
package dict;
import help.WeiboConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.dom4j.DocumentHelper;
import org.eclipse.swt.widgets.Display;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.eclipse.swt.widgets.Display;

import dialog.ComputeSentimentScoreDialog;


public class SentimentAnalysis {
	List<Element> weibos;
	private ComputeSentimentScoreDialog cssd;
	public List<Element> getWeibos() {
		return weibos;
	}

	String pathname;
	String outpath;
	boolean onlyNeg;  //�Ƿ�ֻ���㸺����д�

	public SentimentAnalysis(ComputeSentimentScoreDialog cssd,String pathname, String outpath) {
		super();
		this.cssd=cssd;
		this.pathname = pathname;
		this.outpath = outpath;
	}

/*����������ڷ����Ƿ�ֻ���㸺����д�*/
	public void setOnlyNeg(boolean onlyNeg) {
		this.onlyNeg = onlyNeg;
	}

	/*�����������΢���ı���з�������*/
	public void analysis() throws Exception {
		

		/* ..................���طִʹ���.............................. */
		String argu = "";
		String system_charset = "UTF-8";
		int charset_type = 1;
		int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");
		String nativeBytes = null;
		if (0 == init_flag) {
			nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
			//System.err.println("��ʼ��ʧ�ܣ�ԭ���ǣ� " + nativeBytes);
			intsertConsoleText("\n�ִʹ��߳�ʼ��ʧ�ܣ�\n�������֤�Ƿ��� ");
			return;
		}
	
		/* ...................������дʵ�................................. */
		Map dictionary = Dictionary.getDictionary("nlpir/newWords.xlsx");

		/* ....................��м���................................ */
		Element outroot = new Element("WeiboList");
		Document Doc = new Document(outroot);
		Element elements;
		SAXBuilder builder = new SAXBuilder();
		int count = 0;
		File f = new File(pathname);
		File files[] = f.listFiles();
		long startMili=System.currentTimeMillis();// ��ʱ��ʼ
		int fileNum=0;
		//ÿ��ѭ������һ��XML�ļ�������΢��
		for ( fileNum = 0; fileNum < files.length; fileNum++) {
			if (files[fileNum].isFile()
					&& files[fileNum].getName().endsWith(".xml")) {
				Document document = builder.build(files[fileNum]);// ����ĵ�����
				Element root = document.getRootElement();// ��ø��ڵ�
				List<Element> account = XPath.selectNodes(root, "//΢��/item/�˺�");
				List<Element> content = XPath.selectNodes(root, "//΢��/item/����");
				List<Element> repeat = XPath.selectNodes(root, "//΢��/item/ת��");
				List<Element> comment = XPath.selectNodes(root, "//΢��/item/����");
				List<Element> priase = XPath.selectNodes(root, "//΢��/item/����");
				List<Element> times = XPath.selectNodes(root, "//΢��/item/ʱ��");
				//ÿ��ѭ������һ��XML�ļ���һ��΢��
				for (int i = 0; i < account.size(); i++) {
					if (repeat.get(i).getText().equals(""))
						repeat.get(i).setText("0");
					if (comment.get(i).getText().equals(""))
						comment.get(i).setText("0");
					if (priase.get(i).getText().equals(""))
						priase.get(i).setText("0");
					String sInput = content.get(i).getText();
					nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(
							sInput, 1);
					int nCountKey = 0;
					String nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(
							sInput, 30, true);   //��ȡ�ؼ���
					StringTokenizer st = new StringTokenizer(nativeByte, "#");
					int scoreOne = 0;// ֻ���㸺���
					int scoreTwo = 0;// ֻ���������
					int scoreThree =0; //�������
					StringBuffer sb = new StringBuffer();
					//ÿ��ѭ������һ���ؼ��֣����ݹؼ��ֲ�ѯ��дʵ��ü��Ժ͵÷�
					while (st.hasMoreTokens()) {
						String token = st.nextToken().trim();
						String[] arr = token.split("/");
						if (dictionary.containsKey(arr[0])) {
							int keyNum = Integer.parseInt(arr[3]);
							int polar = ((SeWord) dictionary.get(arr[0]))
									.getPolar();
							int strength = ((SeWord) dictionary.get(arr[0]))
									.getStrength();
							int strengthed = strength * keyNum;
							sb.append(arr[0]);
							sb.append("[");
							if (polar == 2) {
								scoreOne -= strengthed;
								scoreTwo -= strengthed;
								if (keyNum == 1)
									sb.append(-strength);
								else {
									sb.append(-strength);
									sb.append("*");
									sb.append(keyNum);
								}
							} else if (polar == 1) {
								scoreTwo += strengthed;
								scoreThree+= strengthed;
								if (keyNum == 1){
									sb.append(strength);
								}
								else {
									sb.append(strength);
									sb.append("*");
									sb.append(keyNum);
								}
							}
							sb.append("]");
							sb.append("  ,   ");
						}
					}
					/******* �����ܵ�xml�ļ� ***********/
					elements = new Element("΢��");
					elements.addContent(new Element("�˺�").setText(account
							.get(i).getText()));
					elements.addContent(new Element("����").setText(content
							.get(i).getText()));
					elements.addContent(new Element("ת��").setText(repeat.get(i)
							.getText()));
					elements.addContent(new Element("����").setText(comment
							.get(i).getText()));
					elements.addContent(new Element("����").setText(priase.get(i)
							.getText()));
					elements.addContent(new Element("ʱ��").setText(times.get(i)
							.getText()));
					if (onlyNeg) {
						if(scoreOne==0)
							elements.addContent(new Element("��ֵ").setText(scoreThree
									+ ""));
						else 
						    elements.addContent(new Element("��ֵ").setText(scoreOne
								    + "")); // ���ݵ�ѡ��ť���ɵ÷�����
					} else {
						elements.addContent(new Element("��ֵ").setText(scoreTwo
								+ ""));
					}
					elements.addContent(new Element("�ؼ���").setText(sb
							.toString()));
					outroot.addContent(elements);
					
					count++;
				}
			}
			moveProgressBar(fileNum);
			intsertConsoleText("������"+(fileNum+1)+"��XML�ļ�\n");
		}
		long endMili=System.currentTimeMillis();//��ʱ����
		intsertConsoleText("\n------------------------------\n");
		intsertConsoleText("���ƴ���"+(fileNum)+"��XML�ļ�\n");
		intsertConsoleText("���ƴ���"+(count)+"��΢���ı�\n");
		intsertConsoleText("������ʱ��Ϊ"+(endMili-startMili)+"����\n");
		XMLOutputter XMLOut = new XMLOutputter();
		XMLOut.output(Doc, new FileOutputStream(outpath));
		CLibrary.Instance.NLPIR_Exit();
		File file= new File(outpath);
		Document document = builder.build(file);// ����ĵ�����
		Element root = document.getRootElement();// ��ø��ڵ�
		List<Element> weibos = XPath.selectNodes(root, "//΢��");
		
		this.weibos = weibos;

	}
	/**
     * ������ʾ�Ի��������
     * @param progress
     */
	
   private void moveProgressBar(int progress){
        cssd.getDisp().asyncExec(new Runnable() {
            
            @Override
            public void run() {
            	//if(cssd.getpBar().isDisposed()) return ;
                cssd.getpBar().setSelection(progress);
                
            }
        });
    } 
   
    /**
    * ���ڼ�����еĹ�������ʾ��̬��Ϣ
    * @param str
    */
   private void intsertConsoleText(final String str) {
	   cssd.getDisp().syncExec(new Runnable() {
           
           @Override
           public void run() {
               cssd.getConsoleText().insert(str);
               
           }
       });
   }
}