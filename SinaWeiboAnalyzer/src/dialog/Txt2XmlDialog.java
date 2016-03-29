/*
 * ��������ڱ���txt�ļ�ת��Ϊxml�ļ�
 */
package dialog;
import grasp.Txt2Xml;
import help.WeiboConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import root.AnalyzerWindow;


public class Txt2XmlDialog {
	private Display display = Display.getDefault();
	private Shell shell =  new Shell();
	private Text contentpath;
	private Text markpath;
	private Button closebtn;
	private Button OK;
	private AnalyzerWindow analyzerWindow;
	public  Txt2XmlDialog(AnalyzerWindow analyzerWindow){
		this.analyzerWindow = analyzerWindow;
	}
	/**
	 * Open the dialog.
	 */
	public void open() {
		String path = analyzerWindow.getWorkspace().getPath()
				+ analyzerWindow.getProjDirectory().getPath()
				+ WeiboConstants.WEIBO_SRC_DIR+"\\";
///	System.out.println(path);
		shell.setSize(460, 260);
		shell.setText("��������ת��ΪXML");
		
		Group group = new Group(shell, SWT.NONE);
		group.setBounds(10, 0, 420, 80);
		
		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setBounds(10, 20, 130, 17);
		lblNewLabel.setText("΢������txt�ļ�·��Ϊ:");
		
		contentpath = new Text(group, SWT.BORDER);
		contentpath.setBounds(10, 50, 300, 23);
		
		Button btnNewButton = new Button(group, SWT.NONE);
		btnNewButton.setBounds(330, 46, 80, 30);
		btnNewButton.setText("���");
		btnNewButton.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {			
	    		
	    			FileDialog d = new FileDialog(shell);
					d.setText("ѡ���ļ�");
		    		String selectPath = d.open();
		    		contentpath.setText(selectPath);
		    	//	System.out.println(contentpath.getText());	
					
			}
		});
		Group group_1 = new Group(shell, SWT.NONE);
		group_1.setBounds(10, 86, 420, 80);
		
		Label lblNewLabel_1 = new Label(group_1, SWT.NONE);
		lblNewLabel_1.setBounds(10, 20, 130, 17);
		lblNewLabel_1.setText("��б�עtxt�ļ�·��Ϊ:");
		
		markpath = new Text(group_1, SWT.BORDER);
		markpath.setBounds(10, 50, 300, 23);
		
		Button btnNewButton_1 = new Button(group_1, SWT.NONE);
		btnNewButton_1.setBounds(330, 46, 80, 30);
		btnNewButton_1.setText("���");

		btnNewButton_1.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				Shell shell = new Shell();
				FileDialog d = new FileDialog(shell);
				d.setText("ѡ���ļ�");
	    		String selectPath = d.open();
	    		markpath.setText(selectPath);
	    	//	System.out.println(markpath.getText());
			
			}
		});
		
		OK = new Button(shell, SWT.NONE);
		OK.setBounds(241, 184, 80, 27);
		OK.setText("\u786E\u5B9A");
		OK.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e){
            //	System.out.println(path);
            	String news=Txt2Xml.outputXml(Txt2Xml.readFile(contentpath.getText()),
        				Txt2Xml.readMark(markpath.getText()),path);
            	  MessageBox m = new MessageBox(new Shell(Display.getCurrent()),SWT.NO);
            	  m.setText("��Ϣ");
            	  m.setMessage(news);
            	  m.open();
            	shell.close();                         
            }        	
         });
		
		closebtn = new Button(shell, SWT.NONE);
		closebtn.setBounds(350, 184, 80, 27);
		closebtn.setText("\u53D6\u6D88");
		closebtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e){   
            	shell.close();                                    //�ر�
             }
         });
		
		
		shell.open();
		shell.layout();		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	
	}

}
