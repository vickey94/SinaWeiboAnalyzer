
/*�ѱ����õ��ı����ݺͱ�ע�ı�ת��Ϊһ��XML�ļ�*/

package actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import dialog.Txt2XmlDialog;
import root.AnalyzerWindow;

public class TransformDataAction extends Action{
	private AnalyzerWindow analyzerWindow;
	public TransformDataAction(){
		super();
		setText("��������ת��ΪXML");
	}
	
	public void run(){
		System.out.println("TransformDataAction��������������Ǳ�������ת��ΪXML");    //   �������
		if (analyzerWindow.getProjDirectory().getPath() == null) {
	    	  MessageBox m = new MessageBox(new Shell(Display.getCurrent()),SWT.NO);
	    	  m.setText("����");
	    	  m.setMessage("ץȡ΢��ǰ���봴����Ŀ��");
	    	  m.open();

		} else {
		Txt2XmlDialog t = new Txt2XmlDialog(getAnalyzerWindow());		
		t.open();
		}
	}
	public void setAnalyzerWindow(AnalyzerWindow analyzerWindow) {
		this.analyzerWindow = analyzerWindow;
	}
	public AnalyzerWindow getAnalyzerWindow() {
		return analyzerWindow;
	}

}
