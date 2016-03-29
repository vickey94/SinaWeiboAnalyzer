/*ִ��ץȡ΢��
�ȼ�����޴�����Ŀ��Ȼ��ͨ���Ի���GraspWeiboDialog����ץȡ��������ץȡ����XML�ļ����з�����Ŀ�ļ����µ�һ��ר���ļ�����
*/
package weiboAnalyzer.actions;
import help.PathManager;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import dialog.GraspWeiboDialog;

public class GraspWeiboAction extends Action {
	private PathManager workspace, projDirectory;

	public GraspWeiboAction() {
		super();
		setText("ץȡ΢��");
		setToolTipText("ץȡ΢��");
		setImageDescriptor(ImageDescriptor.createFromFile(this.getClass(),
				"grasp20.png"));
	}

	public void setWorkspace(PathManager workspace) {
		this.workspace = workspace;
	}

	public void setProjDirectory(PathManager projDirectory) {
		this.projDirectory = projDirectory;
	}

	public void run() {
		if (projDirectory.getPath() == null) {
	    	  MessageBox m = new MessageBox(new Shell(Display.getCurrent()),SWT.NO);
	    	  m.setText("����");
	    	  m.setMessage("ץȡ΢��ǰ���봴����Ŀ��");
	    	  m.open();

		} else {
			GraspWeiboDialog dialog = new GraspWeiboDialog(new Shell(
					Display.getCurrent()),workspace, projDirectory);
			int openValue = dialog.open();
			
			//System.out.println("GraspWeiboAction����ʾ��Ŀ·��"+workspace.getPath()+projDirectory.getPath());  //�������

		}

	}
}
