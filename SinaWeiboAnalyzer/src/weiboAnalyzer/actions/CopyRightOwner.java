package weiboAnalyzer.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class CopyRightOwner extends Action {
    public CopyRightOwner()
    {
    	this.setText("�������");
    	this.setToolTipText("�������");
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	 Shell  s=new Shell(Display.getCurrent());
	 MessageBox msg=new MessageBox(s);
	  msg.setText("�������");
	  msg.setMessage("������:������Ϣְҵ����ѧԺ"+'\n'+"ָ����ʦ:Ǯ����"+'\n'+"ѧ��:������ ����Ⱥ �ƿ���"+"\n  ��ֹ���⴫����������");
		msg.open();
		
	}
}
