
/*��������ı���У�Ȼ�����ע����бȽϣ�������з�����׼ȷ��*/

package actions;

import help.WeiboConstants;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import root.AnalyzerWindow;
import dialog.ComputeSentimentScoreDialog;
import dialog.GameSentimentScoreDialog;

public class GameSentimentScoreAction extends Action{
	private AnalyzerWindow analyzerWindow;
	public GameSentimentScoreAction(){
		super();
		setText("�����ı���м���");
	}
	public void setAnalyzerWindow(AnalyzerWindow analyzerWindow) {
		this.analyzerWindow = analyzerWindow;
	}
	
	public void run(){
		System.out.println("GameSentimentScoreAction��������������Ǽ�������ı���У�Ȼ�����ע����бȽϣ�������з�����׼ȷ��");    //   �������
		if (analyzerWindow.getProjDirectory().getPath() == null) {
			MessageBox m = new MessageBox(new Shell(Display.getCurrent()),
					SWT.NO);
			m.setText("����");
			m.setMessage("���ȴ������������ļ��в�����������ת��ΪXML�ļ�");
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
				m.setMessage("�뽫������ת��ΪXML�ļ�");
				m.open();
			}
			else{
				GameSentimentScoreDialog dialog = new GameSentimentScoreDialog(new Shell(
						Display.getCurrent()),analyzerWindow);
				int openValue = dialog.open();

			}

		}
	}

}
