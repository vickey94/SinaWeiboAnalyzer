/*ִ���½���Ŀ
ͨ���Ի����ȡ��Ŀ���ƣ���ͬ�����ռ�·����������Ŀ�ļ��У��������д���weiboSrc�ļ���
*/

package actions;

import java.io.File;

import help.PathManager;
import help.WeiboConstants;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import root.AnalyzerWindow;
import dialog.NewProjectDialog;
import dialog.SetBiasDialog;

public class SetBiasAction extends Action{
	private AnalyzerWindow analyzerWindow;
	public SetBiasAction() {
		super();
		setText("����Ԥ����ֵ");
	}

	public void setAnalyzerWindow(AnalyzerWindow analyzerWindow) {
		this.analyzerWindow = analyzerWindow;
	}

	public void run(){
		SetBiasDialog dialog = new SetBiasDialog(new Shell(Display.getCurrent()),analyzerWindow);
		int openValue = dialog.open();
		//System.out.println("Class NewProjectAction get directory:"+analyzerWindow.getProjDirectory().getPath());     //�������
		//System.out.println("Class NewProjectAction get workspace:"+analyzerWindow.getWorkspace().getPath());    //�������
/*		String absPath = analyzerWindow.getWorkspace().getPath()+analyzerWindow.getProjDirectory().getPath(); 
		File projDir = new File(absPath);
		if(!projDir.exists())
		    projDir.mkdir();
		File weiboSrcDir = new File(absPath+WeiboConstants.WEIBO_SRC_DIR);
		if(!weiboSrcDir.exists())
			weiboSrcDir.mkdir();*/
	}

}
