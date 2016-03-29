/*ִ���½���Ŀ
ͨ���Ի����ȡ��Ŀ���ƣ���ͬ�����ռ�·����������Ŀ�ļ��У��������д���weiboSrc�ļ���
*/

package actions;

import java.io.File;
import help.PathManager;
import help.WeiboConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import root.AnalyzerWindow;
import dialog.NewProjectDialog;

public class NewProjectAction extends Action{
	private PathManager workspace,projDirectory;
	AnalyzerWindow analyzerWindow;
	

	public NewProjectAction() {
		super();
		setText("�½���Ŀ");
		setToolTipText("�½���Ŀ");
	    setImageDescriptor(ImageDescriptor.createFromFile(this.getClass(),"newPro20.png"));
		
	}
	

	public void setAnalyzerWindow(AnalyzerWindow analyzerWindow) {
		this.analyzerWindow = analyzerWindow;
		this.workspace = analyzerWindow.getWorkspace();
		this.projDirectory = analyzerWindow.getProjDirectory();
		
	}

	public void run(){
		NewProjectDialog dialog = new NewProjectDialog(new Shell(Display.getCurrent()),analyzerWindow);
		int openValue = dialog.open();
		System.out.println("Class NewProjectAction get directory:"+projDirectory.getPath());     //�������
		System.out.println("Class NewProjectAction get workspace:"+workspace.getPath());    //�������
		String absPath = workspace.getPath()+projDirectory.getPath(); 
		File projDir = new File(absPath);
		if(!projDir.exists())
		    projDir.mkdir();
		File weiboSrcDir = new File(absPath+WeiboConstants.WEIBO_SRC_DIR);
		if(!weiboSrcDir.exists())
			weiboSrcDir.mkdir();
	}

}
