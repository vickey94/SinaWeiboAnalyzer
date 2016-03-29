
/*
 * WorkspaceDialog������ϵͳ����ʱ�趨�����ռ�·��
 * 
 * */

package dialog;

import help.PathManager;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class WorkspaceDialog extends Dialog
{
  private static final int RESET_ID = 
                  IDialogConstants.NO_TO_ALL_ID + 1;
  
  private Text path;
  PathManager workspace;
  public WorkspaceDialog(Shell parentShell,PathManager workspace)
  {
    super(parentShell);
    this.workspace = workspace;
    
  }

  protected Control createDialogArea(Composite parent)
  {
    Composite comp = (Composite)super.createDialogArea(parent);
    FillLayout layout = new FillLayout(SWT.VERTICAL);
    layout.spacing = 5;
    Label winLabel = new Label(comp, SWT.CENTER);
    winLabel.setText("ѡ�����ռ�");
    Display disp = Display.getCurrent();
    FontData fontdata = new FontData("Arial", 11, SWT.BOLD);
    Font font = new Font(disp,fontdata);
    winLabel.setFont(font);
    
    Label annotation = new Label(comp, SWT.CENTER);
    annotation.setText("�������ռ��Ǳ�����Ŀ��Դ���ļ��У�");
    
    Group c = new Group(comp,SWT.SHADOW_ETCHED_IN);
  
    Label spaceAnno = new Label(c, SWT.LEFT);
    spaceAnno.setText("�����ռ䣺");
    spaceAnno.setBounds(0, 18, 55, 15);
    path = new Text(c, SWT.SINGLE);
    path.setText("E:\\weiboSpace");        //����Ĭ�Ϲ����ռ�
    path.setBounds(60, 15, 200, 20);
    Button b = new Button(c, SWT.PUSH);
    b.setText("���...");
    b.setBounds(270, 10, 80, 30);
    b.addSelectionListener(new SelectionAdapter(){
    	public void widgetSelected(SelectionEvent e) {
    		Shell shell = new Shell(disp);
    		DirectoryDialog d = new DirectoryDialog(shell);
    		d.setMessage("ѡ���ļ���");
    		String selectPath = d.open();
    		try {
				path.setText(selectPath);
				System.out.println(path.getText());
			} catch (IllegalArgumentException e1) {
				
			}
    		
    		
    	}
    });
    return comp;
  }
  
  protected void createButtonsForButtonBar(Composite parent)
  {
    createButton(parent, OK, "ȷ��", false);
    createButton(parent, CANCEL, "ȡ��", false);
    createButton(parent, RESET_ID, "����", false);
  }
  
  protected void buttonPressed(int buttonId)
  {
	  System.out.println(buttonId);
    if(buttonId == OK)    //��������ȷ����ť���ȼ���Ƿ�Ϊ�Ϸ�·��
    {
    	File f = new File(path.getText().trim());
      if (!f.isDirectory())
      {  
    	   if(f.mkdirs())
    	   {
    	  MessageBox m = new MessageBox(new Shell(Display.getCurrent()),SWT.NO);
    	  m.setText("��������");
    	  m.setMessage("·��"+path.getText().trim()+"\n   �Ѿ�����");
    	  m.open();
    	  workspace.setPath(path.getText());
          super.buttonPressed(buttonId);
    	   }
    	   else
    	   {
    		   MessageBox m = new MessageBox(new Shell(Display.getCurrent()),SWT.NO);
    	    	  m.setText("��������");
    	    	  m.setMessage("������Ϸ�·��");
    	    	  m.open();
    	   }
      }
      else{
    	  //System.out.println(path.getText()+"....from OK");	  //�������
    	  workspace.setPath(path.getText());
          super.buttonPressed(buttonId);
      }
      
    }
    else if(buttonId == RESET_ID)      //��������谴ť
    	path.setText("");
    else             //ȡ����ť
    {
    	System.exit(0);
    }
  }
  
  

}