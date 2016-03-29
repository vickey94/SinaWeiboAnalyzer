package dialog;

import java.io.File;

import help.PathManager;
import help.WeiboConstants;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dict.GameSentimentAnalysis;
import dict.SentimentAnalysis;
import root.AnalyzerWindow;

public class GameSentimentScoreDialog extends Dialog {
	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
	private Button[] radios = new Button[2];
	private PathManager workspace, projDirectory;
	private AnalyzerWindow analyzerWindow;
	private Display disp;
	private ProgressBar pBar;  //����������
	private Text consoleText; //������������ı���
	boolean onlyNeg;
	
	GameSentimentAnalysis sa;

	
	
	public GameSentimentScoreDialog(Shell parentShell,
			AnalyzerWindow analyzerWindow) {
		super(parentShell);
		this.workspace = analyzerWindow.getWorkspace();
		this.projDirectory = analyzerWindow.getProjDirectory();
		this.analyzerWindow = analyzerWindow;
	}

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite) super.createDialogArea(parent);
		comp.setSize(600, 800);
		Label winLabel = new Label(comp, SWT.CENTER);
		winLabel.setText("�����ı���м���");
		//Display disp = Display.getCurrent();,
	    disp = Display.getDefault();
		FontData fontdata = new FontData("Arial", 11, SWT.BOLD);
		Font font = new Font(disp, fontdata);
		winLabel.setFont(font);

		Group c = new Group(comp, SWT.SHADOW_ETCHED_IN);
		c.setLayout(new GridLayout(1, false));
		Group r1 = new Group(c, SWT.SHADOW_NONE);
		r1.setLayout(new GridLayout(1, false));
		radios[0] = new Button(r1, SWT.RADIO);
		radios[0].setText("ֻ���㸺����дʵ��ۼ�");
		radios[0].setSelection(true);
		radios[0].pack();
		radios[1] = new Button(r1, SWT.RADIO);
		radios[1].setText("��������͸�����дʵ��ۼ�");
		radios[1].pack();
		Button b =new Button(r1, SWT.NONE);
		 b.setText("��ʼ����"); 
		Group r2 = new Group(c, SWT.SHADOW_NONE);
		r2.setLayout( new FormLayout());
	    pBar = new ProgressBar(r2, SWT.HORIZONTAL);
		FormData fd_bar = new FormData();
		fd_bar.top = new FormAttachment(0);
		fd_bar.right = new FormAttachment(100, -10);
		fd_bar.bottom = new FormAttachment(0, 20);
		fd_bar.left = new FormAttachment(100, -200);
		pBar.setLayoutData(fd_bar);
	    
		consoleText = new Text(r2, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(pBar,5,SWT.BOTTOM);
		fd_text.right = new FormAttachment(pBar,0, SWT.RIGHT);
		fd_text.bottom = new FormAttachment(pBar,100,SWT.BOTTOM);
		fd_text.left = new FormAttachment(pBar,0, SWT.LEFT);
		consoleText.setLayoutData(fd_text);
 
			
			String srcPath = workspace.getPath() + projDirectory.getPath()
					+ WeiboConstants.WEIBO_SRC_DIR; // ץȡ����΢������·��
			/****************************����Ⱥ���*********************************************/
			String outPath = workspace.getPath() + projDirectory.getPath()
					+WeiboConstants.OBJ_XML_FILE;//����xml�ļ�����·��  
			/**********************************************************************************/
		 sa = new GameSentimentAnalysis(this, srcPath, outPath);
			File srcDir = new File(srcPath);
			String xmlFiles[] = srcDir.list();
			int taskCount=xmlFiles.length;
			pBar.setMaximum(taskCount);
		 b.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				boolean onlyNeg = true;
				if (radios[1].getSelection())
					onlyNeg = false;
				    sa.setOnlyNeg(onlyNeg);

				// �����д������еĴ��루�������ã� �� ��д����
				/****************************����Ⱥ���*********************************************/
				
				 consoleText.insert("��ʼ����΢������\n");
	             new Thread(){                                    //Ϊ��̨���񴴽�һ���µ��߳�
	                 public void run(){
	                     try {
							sa.analysis();
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                 }
	             }.start();

				/**********************************************************************************/
					System.out.println("OK");
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		return comp;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, OK, "�رմ���", false);
		//createButton(parent, CANCEL, "ȡ��", false);
		
		// createButton(parent, RESET_ID, "����", false);
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == OK) // ��������ȷ����ť
		{
			    
			analyzerWindow.getKeyWord_text().setText("");
			analyzerWindow.getWeibo_text().setText("");
			analyzerWindow.callInput(sa.getWeibos());
			
				System.out.println("OK");
		}

		super.buttonPressed(buttonId);

	}

	public Display getDisp() {
		return disp;
	}

	public ProgressBar getpBar() {
		return pBar;
	}

	public void setpBar(ProgressBar pBar) {
		this.pBar = pBar;
	}

	public Text getConsoleText() {
		return consoleText;
	}

	public void setConsoleText(Text consoleText) {
		this.consoleText = consoleText;
	}

	

}
