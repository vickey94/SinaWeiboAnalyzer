/*
 * ���ڴ���Ŀ�������������е���Ŀ���ƺ���Ŀ·��
 * 
 * */

package dialog;
import help.PathManager;
import help.WeiboConstants;
import java.io.File;
import java.util.List;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import root.AnalyzerWindow;

public class OpenProjectDialog extends Dialog {
	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
	private Combo existedProj;
	private Text proj_name, proj_path;  // ����������洫�����ı���
	private PathManager workspace, projDirectory;
	TableViewer viewer;
	AnalyzerWindow analyzerWindow;

	public OpenProjectDialog(Shell parentShell, AnalyzerWindow analyzerWindow) {
		super(parentShell);
		this.analyzerWindow = analyzerWindow;
		this.workspace = analyzerWindow.getWorkspace();
		this.projDirectory = analyzerWindow.getProjDirectory();
		this.proj_name = analyzerWindow.getProj_name();
		this.proj_path = analyzerWindow.getProj_path();
		this.viewer = analyzerWindow.getViewer();
	}

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite) super.createDialogArea(parent);
		Label winLabel = new Label(comp, SWT.CENTER);
		winLabel.setText("ѡ���Ѵ�������Ŀ");
		Display disp = Display.getCurrent();
		FontData fontdata = new FontData("Arial", 11, SWT.BOLD);
		Font font = new Font(disp, fontdata);
		winLabel.setFont(font);
		Group c = new Group(comp, SWT.SHADOW_ETCHED_IN);
		Label spaceAnno = new Label(c, SWT.LEFT);
		spaceAnno.setText("��ѡ��Ŀ��");
		spaceAnno.setBounds(0, 18, 55, 15);
		existedProj = new Combo(c, SWT.READ_ONLY);
		existedProj.setBounds(60, 15, 200, 20);
		// System.out.println("Testing from OpenProjectDialog :"+workspace.getPath());
		// //�������
		File workspaceDir = new File(workspace.getPath());
		String projDir[] = workspaceDir.list();
		for (int i = 0; i < projDir.length; i++) {
			existedProj.add(projDir[i]);

			// System.out.println("Testing from OpenProjectDialog :"+projDir[i]);
			// //�������
		}
		return comp;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, OK, "ȷ��", false);
		createButton(parent, CANCEL, "ȡ��", false);
		// createButton(parent, RESET_ID, "����", false);
	}

	protected void buttonPressed(int buttonId) {
		// System.out.println(buttonId); //�������
		if (buttonId == OK) // ��������ȷ����ť���ȼ���Ƿ�Ϊ�Ϸ�·��
		{
			projDirectory.setPath("\\" + existedProj.getText() + "\\");
			proj_name.setText(existedProj.getText()); // �����������е���Ŀ����
			proj_path.setText(workspace.getPath() + "\\"
					+ existedProj.getText()); // �����������е���Ŀ·��

/*			System.out.println("Testing from OpenProjectDialog123 :"
					+ workspace.getPath() + projDirectory.getPath()
					+ WeiboConstants.OBJ_XML_FILE+"END"); // �������
*/
			File xmlFile = new File(workspace.getPath() + projDirectory.getPath()+ WeiboConstants.OBJ_XML_FILE);
			//File file = new File("C:\\eclipse_plugIns\\workspace\\writeXml\\weiboData.xml");
		
			List<Element> weibos = null;
			if (xmlFile.exists()) {
			//	System.out.println("Testing !!!!");
				System.out.println(xmlFile.getName());
				try {
					SAXBuilder builder = new SAXBuilder();
					Document document = builder.build(xmlFile);// ����ĵ�����
					Element root = document.getRootElement();// ��ø��ڵ�
					weibos = XPath.selectNodes(root,"//WeiboList/΢��");///�˺�		
				/*	for (int i = 0; i < weibos.size(); i++) {
						System.out.println("�û�����"+weibos.get(i).getChildText("�˺�")+ "\n"+
                                           "���ݣ�" +weibos.get(i).getChildText("����")+ "\n"+
                                           "ת����:" +weibos.get(i).getChildText("ת��"));
					}*/
				} catch (Exception e) {
					System.out.println("XML �����쳣��");
				}
			}
			//���������ؼ����ı����΢���ı���
        	analyzerWindow.getKeyWord_text().setText("");
        	analyzerWindow.getWeibo_text().setText("");
			// ���������΢��Ԥ��������д����
			analyzerWindow.callInput(weibos);
			super.buttonPressed(buttonId);
		} else
			// ȡ����ť
			super.buttonPressed(buttonId);
	}

}