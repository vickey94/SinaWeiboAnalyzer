/*ִ����м���
�ȼ�����޴�����Ŀ������Ŀ�ļ����б���XML���ļ����л�ȡÿ��XML�ļ������㴦��������һ��XML�ļ�
*/


package weiboAnalyzer.actions;

import java.io.File;

import help.PathManager;
import help.WeiboConstants;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import root.AnalyzerWindow;
import dialog.ComputeSentimentScoreDialog;

public class ComputeSentimentScoreAction extends Action {
	//private PathManager workspace, projDirectory;
	private AnalyzerWindow analyzerWindow;

	public ComputeSentimentScoreAction() {
		super();
		setText("��м��������");
		setToolTipText("��м��������");
		setImageDescriptor(ImageDescriptor.createFromFile(this.getClass(),
				"comp2.png"));
	}
	

	public void setAnalyzerWindow(AnalyzerWindow analyzerWindow) {
		this.analyzerWindow = analyzerWindow;
	}


/*	public void setWorkspace(PathManager workspace) {
		this.workspace = workspace;
	}

	public void setProjDirectory(PathManager projDirectory) {
		this.projDirectory = projDirectory;
	}*/

	public void run() {
		if (analyzerWindow.getProjDirectory().getPath() == null) {
			MessageBox m = new MessageBox(new Shell(Display.getCurrent()),
					SWT.NO);
			m.setText("����");
			m.setMessage("��м���ǰ���봴����Ŀ��ץȡ΢����");
			m.open();
		} else {
			String srcPath = analyzerWindow.getWorkspace().getPath() + analyzerWindow.getProjDirectory().getPath()
					+ WeiboConstants.WEIBO_SRC_DIR;      //ץȡ����΢������·��
			System.out.println(srcPath);               //�������
			File srcDir = new File(srcPath);
			String xmlFiles[] = srcDir.list();
			if (xmlFiles.length == 0) {
				MessageBox m = new MessageBox(new Shell(Display.getCurrent()),
						SWT.NO);
				m.setText("����");
				m.setMessage("��м���ǰ����ץȡ΢����");
				m.open();
			}
			else{
				ComputeSentimentScoreDialog dialog = new ComputeSentimentScoreDialog(new Shell(
						Display.getCurrent()),analyzerWindow);
				int openValue = dialog.open();

			}

		}

	}
}
