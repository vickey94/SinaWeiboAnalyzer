/*�����࣬���öԻ���ShowAllWeiboDialog���л���ʾ����΢�����ǽ���ʾ����΢��
*/

package weiboAnalyzer.actions;

import java.io.File;

import help.PathManager;
import help.WeiboConstants;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import root.AnalyzerWindow;
import dialog.NewProjectDialog;
import dialog.OpenProjectDialog;
import dialog.ShowAllWeiboDialog;

public class ShowAllWeiboAction extends Action{
	AnalyzerWindow analyzerWindow;
	

	public ShowAllWeiboAction() {
		super();
		setText("��ʾ����΢��/����ʾ����΢��");
		
	}

	public void setAnalyzerWindow(AnalyzerWindow analyzerWindow) {
		this.analyzerWindow = analyzerWindow;
	}

	public void run(){
		ShowAllWeiboDialog dialog = new ShowAllWeiboDialog(new Shell(Display.getCurrent()),analyzerWindow);
		int openValue = dialog.open();

	}

}
