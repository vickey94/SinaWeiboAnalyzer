/* 
 * ΢��ץȡ������
 * ��Ҫ�Ĳ�����΢���˺�,����,�ؼ��ʣ���ѡ��,ʱ�䣨��ѡ��,��ַ
 * 
 */

package grasp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import dialog.YzmDialog;

public class SinaWeibo {

	protected static int wbcount = 0; // ����������ͳ��΢��ץȡ������
	private final static WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24); // ����Ĭ�������ΪFIREFOX_24

	/** ����������ڵ�¼����΢�������ص�¼��� **/
	protected static String login(String username, String password) {
		
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		HtmlPage page;
		try {
			page = webClient.getPage("https://login.weibo.cn/login/");
			
			//XPATH��ʽ������˺š�����������������������nameֵΪpassword_xxxx������λΪ����������Բ���XPATH����
			List<?> inputList1 = page
					.getByXPath("/html/body/div[position()=2]/form/div/input[position()=1]");
			HtmlInput input1 = (HtmlInput) inputList1.get(0);
			input1.setValueAttribute(username); // �����˺�

			List<?> inputList2 = page
					.getByXPath("/html/body/div[position()=2]/form/div/input[position()=2]");
			HtmlInput input2 = (HtmlInput) inputList2.get(0);
			input2.setValueAttribute(password); // ��������

			// ��ȡsumbit�ύ��ť;
			final List<HtmlForm> formList = page.getForms(); // ��Ϊ����΢��form��û��nameֵ�������޷���getFormByName("form")�������;
			final HtmlForm form = formList.get(0);
			final HtmlSubmitInput button = form.getInputByName("submit");
			HtmlPage newPage = null;
			try {
				newPage = button.click(); // ģ���������ҳ��
			//	System.out.println(newPage.asText());
			} catch (RuntimeException e) { // �������RuntimeException�쳣��˵�����粻ͨ
				e.printStackTrace();
				return "��ȷ�����糩ͨ��\n";
			}

			// ���ڻ�õ�¼���������¼���Ϊ����¼�ɹ�����¼�������������������֤���������
			List<?> balanceList3 = newPage
					.getByXPath("/html/body/div[position()=2]/text()");
			// System.out.println(balanceList3.get(0).toString());
			if (balanceList3.get(0).toString().trim().equals("��¼�����������")) {
				return "΢���˺ź����벻��ȷ��\n";
			}
			if (balanceList3.get(0).toString().trim().equals("��֤�����")) {
				// �����֤�����ҳ��ַ
				HtmlImage yzmImage=  (HtmlImage)newPage.getElementsByTagName("img").get(0);

				// ������֤����ʾ��
				YzmDialog yzmDialog = new YzmDialog(new Shell(new Display()),
						SWT.CLOSE | SWT.ON_TOP);
			
				//���д���֤��ĵ�¼�����ص�¼���
				return codeLogin(newPage, username, password, yzmDialog.open(yzmImage));
			}
			if (balanceList3.get(0).toString().trim().equals("��ӭ����΢��")){
				return "�������˺�����\n";
			}
			else {
				return "��½�ɹ���\n";
			}
		} catch (FailingHttpStatusCodeException | IOException e) {
			return "��ȷ�����糩ͨ��\n";
		}
	}

	/**����������ڴ���֤���¼����΢�������ص�¼���**/
	private static String codeLogin(HtmlPage page, String username,
			String password, String code) throws IOException {

		// XPATH ��ʽ������˺ź����룬��֤��
		List<?> inputList1 = page
				.getByXPath("/html/body/div[position()=3]/form/div/input[position()=1]");

		HtmlInput input1 = (HtmlInput) inputList1.get(0);
		input1.setValueAttribute(username);

		List<?> inputList2 = page
				.getByXPath("/html/body/div[position()=3]/form/div/input[position()=2]");
		HtmlInput input2 = (HtmlInput) inputList2.get(0);
		input2.setValueAttribute(password);

		List<?> inputList3 = page
				.getByXPath("/html/body/div[position()=3]/form/div/input[position()=3]");
		HtmlInput input3 = (HtmlInput) inputList3.get(0);
		try{
		input3.setValueAttribute(code);
		}catch(NullPointerException e){
			return "��������֤�룡\n";
		}
		List<HtmlForm> formList = page.getForms();
		HtmlForm form = formList.get(0);
		HtmlSubmitInput button = form.getInputByName("submit");
		HtmlPage newPage = null;
		newPage = button.click();// ģ����
	//	System.out.println(newPage.asText());

		List<?> balanceList3 = null;
		try {
			balanceList3 = newPage
					.getByXPath("/html/body/div[position()=2]/text()");
		} catch (IndexOutOfBoundsException e) {
			return "��ȷ�����糩ͨ��\n";
		}  
		if (balanceList3.get(0).toString().trim().equals("��֤�����")
				|| balanceList3.get(0).toString().trim().equals("��������֤��")) {
			return "��֤����������µ�¼��\n";
		} else {
			return "��½�ɹ���\n";
		}

	}
  /**�ж��Ƿ��¼����΢���˺�**/
  protected static boolean whetherLogin() throws Exception{	

		HtmlPage page = webClient.getPage("https://weibo.cn");	
	//	page.refresh();
		List<?> markList = page
				.getByXPath("/html/body/div[position()=2]/div/a/text()");
	//	System.out.println(page.asText());
		if(markList.size()==2)  return false ;
		else return true;

}
	/**������ַ**/
	protected static String URLEncode(String keyword, boolean sort,String starttime,
			String endtime) {
		String strUrl = "http://weibo.cn/search/mblog?hideSearchFrame=&keyword=";
		String strKeyword = null;   // %E8%88%AA%E7%A9%BA+%E6%97%85%E6%B8%B8
		String strTime = null;
		String strSort = null;

		if (keyword.equals("") || keyword.equals(null)) {
			keyword = "�� ����";
		}
		try {
			strKeyword = URLEncoder.encode(keyword, "UTF-8");//encode�Թؼ��ּ���
		} catch (UnsupportedEncodingException e) {	
			e.printStackTrace();
		} //encode�Թؼ��ּ���
		if (starttime.equals("") || endtime.equals("")) {
			strTime = "&filter=hasori";
		} else {
			strTime = "&advancedfilter=1&hasori=1&starttime=" + starttime
					+ "&endtime=" + endtime;
		}
		if(sort) strSort = "&sort=time&page=";
		else strSort = "&sort=hot&page=";
		String strURL = strUrl + strKeyword + strTime + strSort;
	 //   System.out.println(strURL+"1");
		return strURL;
	}
    /**��ȡ����������ҳ΢������**/
	protected static int getPage(String URL){
		// �õ�������������΢������
		HtmlPage page = null;
		try {
			page = webClient.getPage(URL + "1");
		} catch (FailingHttpStatusCodeException | IOException e1) {
			e1.printStackTrace();
		}

		List<?> pagelist = null;
		// ���ѭ����ȡ�����ж���ҳ
		for (int i = 2; i <= 6; i++) {
			if (page.getByXPath(
					"/html/body/div[position()=" + i + "]/span/text()").size() == 0) {
				continue;
			} else {
				pagelist = page.getByXPath("/html/body/div[position()=" + i
						+ "]/span/text()");
				// / System.out.println(pagelist.get(0));
				break;
			}
		}
		// ������ʽƥ�������
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		int n = 100;
		try {
			Matcher m = p.matcher(pagelist.get(0).toString());
			int pagenum = Integer.parseInt(m.replaceAll("").trim());
			if (pagenum >= 1000) {
				n = 100;
			} else {
				if (pagenum % 10 == 0) {
					n = pagenum / 10;
				} else {
					n = (pagenum / 10) + 1;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			n = 100;
			// / System.out.println("δ��ö���ҳ,Ĭ��ץȡ100ҳ");
		}
		return n;
	}

	/**���xml�ļ�**/
	protected static void outputXml(Document doc, String path) throws IOException {
		try {
			Format format = Format.getPrettyFormat();
			XMLOutputter XMLOut = new XMLOutputter(format);
			XMLOut.output(doc, new FileOutputStream(path));
		//	System.out.println("���XML�ɹ���"+path);
		} catch (FileNotFoundException e) {
		//	System.out.println("ָ����·�������ڣ�");
		}
	}

	/**ͳһʱ���ʽ**/
/*	private static String formatTime(String time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");// �������ڸ�ʽ
		
		return df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}
*/	
	
    /**�����������ץȡ΢��������xml�ļ����棬�����ļ���
     * @throws IOException **/
	protected static String catchWeibo(String strUrl, String path, int i){
		
		HtmlPage page3=null;
		try {
			page3 = webClient.getPage(strUrl + i);
		} catch (FailingHttpStatusCodeException | IOException e1) {
			return "��ȷ�����糩ͨ��";
		}

		List<Integer> listX = new ArrayList<Integer>();
		 //���ѭ�������ҵ���΢�����ݵ�DIV�У���list�洢
		for (int j = 3; j <= 30; j++) {
			String strX = "/html/body/div[position()=" + j + "]/div";
			if (page3.getByXPath(strX).size() == 1
					|| page3.getByXPath(strX).size() == 2)
				listX.add(j);
		}
		Iterator<Integer> lx = listX.iterator();

		// �������ڵ� �������������� ;
		Element root = new Element("΢��").setAttribute("count",
				String.valueOf(listX.size())); // ��һҳ�ж�����΢��
		// if(listX.size()==0) return false; //�ж�ҳ���Ƿ���΢�����ݣ����û�о�ֹͣץȡ;
		// �����ڵ���ӵ��ĵ���;
		while (lx.hasNext()) {
			wbcount++;
			String strX = "/html/body/div[position()=" + lx.next() + "]/div";
			String sbWb = null; // �����˺�΢���Ĳ���
			String sbData = null;// �������ݵĲ���

			// ����ͼƬ��DIV��ֻ����1��DIVҪ���ݡ� ��\\[�����и�
			if (page3.getByXPath(strX).size() == 1) {
				HtmlDivision wb = (HtmlDivision) page3.getByXPath(strX).get(0);
				String sss[] = wb.asText().split(" ��\\[");
				sss[1] = "��[" + sss[1];
				sbWb = sss[0];
				sbData = sss[1];
			}
			// ��ͼƬ�İ���2��DIV
			else {
				HtmlDivision wb = (HtmlDivision) page3.getByXPath(strX).get(0);// �˺ź����ݲ��ֵ�DIV
				HtmlDivision data = (HtmlDivision) page3.getByXPath(strX)
						.get(1);// ���ݲ��ֵ�DIV
				sbWb = wb.asText();
				sbData = data.asText();
			}
			String w[] = sbWb.split(":", 2); // �г��˺ź�΢������
			// System.out.println(w[0].toString());
			// System.out.println(w[1].toString());
			String s[] = sbData.split("�ղ�"); // �ѡ����ݡ��г�2��
			// ������ʽƥ����ȡ����
			String re1 = ".*?"; // Non-greedy match on filler
			String re2 = "(\\d+)"; // Integer Number 1
			Pattern p = Pattern.compile(re1 + re2 + re1 + re2 + re1 + re2,
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(s[0]); // s[0]
			String zf = null, pl = null, dz = null;
			if (m.find()) {
				dz = m.group(1);
				zf = m.group(2);
				pl = m.group(3);
				// System.out.println(dz);
			}
			String ss[] = s[1].split("����"); //�õ�ʱ��
			// System.out.println(ss[0].trim()); // ȥ����λ�ո�
//
			// ���ñ���XML
			// �����ڵ� item;
			Element elements = new Element("item");
			// ��item�ڵ�����ӽڵ㲢��ֵ��
			elements.addContent(new Element("�˺�").setText(w[0].toString()));
			elements.addContent(new Element("����").setText(w[1].toString()));
			elements.addContent(new Element("ʱ��").setText(ss[0].trim()
					.toString()));
			elements.addContent(new Element("ת��").setText(zf));
			elements.addContent(new Element("����").setText(pl));
			elements.addContent(new Element("����").setText(dz));
			root.addContent(elements);
		}
		Document doc = new Document(root);
		// System.out.println("ץȡ΢���ɹ���");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");// �������ڸ�ʽ
		// ����outputXml()���XML�ļ�����ϵͳ��ǰʱ����������

		String fileName = df.format(new Date()) + "-" + i + ".xml"; // �ļ�����
		try {
			outputXml(doc, path + fileName);
		} catch (IOException e) {
			return e.getMessage();
		}

		return fileName;
	}

	/**�ر������**/
	protected static void close() {
	
        webClient.getCookieManager().clearCookies();
		webClient.closeAllWindows(); // �ر�
	}

}
