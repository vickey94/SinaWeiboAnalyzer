package dict;

import help.WeiboConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import dialog.ComputeSentimentScoreDialog;
import dialog.GameSentimentScoreDialog;

public class GameSentimentAnalysis {
	List<Element> weibos;
	private GameSentimentScoreDialog cssd;
	public List<Element> getWeibos() {
		return weibos;
	}

	String pathname;
	String outpath;
	boolean onlyNeg;

	public GameSentimentAnalysis(GameSentimentScoreDialog cssd,String pathname, String outpath) {
		super();
		this.cssd=cssd;
		this.pathname = pathname;
		this.outpath = outpath;
	}


	public void setOnlyNeg(boolean onlyNeg) {
		this.onlyNeg = onlyNeg;
	}


	public void analysis() throws Exception {

		/* ................................................................ */
		String argu = "";
		String system_charset = "UTF-8";
		int charset_type = 1;
		int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");
		String nativeBytes = null;
		if (0 == init_flag) {
			nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
			System.err.println("��ʼ��ʧ�ܣ�ԭ���ǣ� " + nativeBytes);
			return;
		}
	
		/* ................................................................ */
		Map dictionary = Dictionary.getDictionary("nlpir/newWords.xlsx");
		Element outroot = new Element("WeiboList");
		Document Doc = new Document(outroot);
		Element elements;
		/* ................................................................ */
		SAXBuilder builder = new SAXBuilder();
		int count = 0;
		double loseRate=0;//��ʧ��
		double mistakeRate=0;//������
		double markFalseNum=0;//��עΪ����΢������
		double markFalseandScoreFalse=0;//��עΪ���ҷ�ֵΪ��������
		double afterCompMinusNum=0;//������ֵС��0������
		File f = new File(pathname);
		File files[] = f.listFiles();
		long startMili=System.currentTimeMillis();// ��ʱ��ʼ
		int fileNum=0;
	
		for ( fileNum = 0; fileNum < files.length; fileNum++) {
			if (files[fileNum].isFile()
					&& files[fileNum].getName().endsWith(".xml")) {
				Document document = builder.build(files[fileNum]);// ����ĵ�����
				Element root = document.getRootElement();// ��ø��ڵ�
				List<Element> content = XPath.selectNodes(root, "//WeiboList/΢��/����");
				List<Element> mark = XPath.selectNodes(root, "//WeiboList/΢��/��ע");
				for (int i = 0; i < content.size(); i++) {
					String sInput = content.get(i).getText();
					nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(
							sInput, 1);
					int nCountKey = 0;
					String nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(
							sInput, 30, true);
					StringTokenizer st = new StringTokenizer(nativeByte, "#");
					int score=0;
					int scoreOne = 0;// ֻ���㸺���
					int scoreTwo = 0;// ֻ���������
					int scoreThree =0; //�������
					StringBuffer sb = new StringBuffer();
					while (st.hasMoreTokens()) {
						String token = st.nextToken().trim();
						String[] arr = token.split("/");
						if (dictionary.containsKey(arr[0])) {
							// System.out.print(((SeWord)dictionary.get(arr[0])).toString());
							// System.out.print("���ִ��� "+arr[3]+"||");
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
					elements.addContent(new Element("����").setText(content
							.get(i).getText()));
					elements.addContent(new Element("��ע").setText(mark
							.get(i).getText()));
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
					if (onlyNeg) {
						score=scoreOne;
					}else {
						score=scoreTwo;
					}
					if(mark.get(i).getText().equals(WeiboConstants.WORD_FOR_NEGATIVE)){
						markFalseNum++;//��עΪ����΢������
						if (score<0){
							System.out.println("weibo"+sInput);
							markFalseandScoreFalse++;
						}
					}
					if (score<0) {
						afterCompMinusNum++;
					}
					
					count++;
				}
			}
			moveProgressBar(fileNum);
			intsertConsoleText("������"+(fileNum+1)+"��XML�ļ�\n");
		}
		long endMili=System.currentTimeMillis();//��ʱ����
		loseRate=1-(markFalseandScoreFalse/markFalseNum);
		mistakeRate=1-(markFalseandScoreFalse/afterCompMinusNum);
		intsertConsoleText("\n------------------------------\n");
		intsertConsoleText("���ƴ���"+(fileNum)+"��XML�ļ�\n");
		intsertConsoleText("���ƴ���"+(count)+"��΢���ı�\n");
		intsertConsoleText("������ʱ��Ϊ"+(endMili-startMili)+"����\n");
		intsertConsoleText("��ʧ��Ϊ"+(String.format("%.2f", loseRate*100))+"%\n");
		intsertConsoleText("������Ϊ"+(String.format("%.2f", mistakeRate*100))+"%\n");
		XMLOutputter XMLOut = new XMLOutputter();
		// ��� weiboData.xml �ļ���
		XMLOut.output(Doc, new FileOutputStream(outpath));
		CLibrary.Instance.NLPIR_Exit();
		File file= new File(outpath);
		Document document = builder.build(file);// ����ĵ�����
		Element root = document.getRootElement();// ��ø��ڵ�
		List<Element> weibos = XPath.selectNodes(root, "//΢��");
		
		this.weibos = weibos;

	}
	/**
     * ��ʾ������
     * @param progress
     */
	
   private void moveProgressBar(int progress){
        cssd.getDisp().asyncExec(new Runnable() {
            
            @Override
            public void run() {
                cssd.getpBar().setSelection(progress+1);
                
            }
        });
    }    
	/*
    * �ı�����
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