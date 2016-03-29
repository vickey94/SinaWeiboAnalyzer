/*
 * ΢��ץȡ����
 * ����ץȡ΢��ǰ���ò���������΢���˺ţ����룬�ؼ��֣�΢������ʱ�䷶Χ
 * 
 */

package dialog;



import grasp.GraspProcess;

import help.PathManager;
import help.WeiboConstants;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.RowData;


public class GraspWeiboDialog extends Dialog {
//	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
	private Text accountText;
	private Text passwordText;
	private Text keyText;
	private Text fromText;  //ץȡ΢��ʱ��εĿ�ʼʱ��
	private Text toText;    //ץȡ΢��ʱ��εĽ���ʱ��
	private PathManager workspace, projDirectory;
	private Button graspButton;        //ץȡ��ť
	private Button loginButton;        //��¼��ť
	private Button exitButton;         //�˳���ť
	private ProgressBar progressBar;  //������
	private Text consoleText;         //ץȡ΢��ʵʱ��Ϣ��ʾ��
	private GraspProcess task = new GraspProcess(this);           

	public GraspWeiboDialog(Shell parentShell, PathManager workspace,
			PathManager projDirectory) {
		super(parentShell);
		this.workspace = workspace;
		this.projDirectory = projDirectory;
	}

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite) super.createDialogArea(parent);

		Label winLabel = new Label(comp, SWT.CENTER);
		winLabel.setText("����ץȡ����");
		Display disp = Display.getCurrent();
		FontData fontdata = new FontData("Arial", 11, SWT.BOLD);
		Font font = new Font(disp, fontdata);
		winLabel.setFont(font);

		Group c = new Group(comp, SWT.SHADOW_ETCHED_IN);
		c.setLayout(new GridLayout(1, true));
		Group r1 = new Group(c, SWT.SHADOW_NONE);
		r1.setLayout(new GridLayout(3, true));
		Label accountLabel = new Label(r1, SWT.CENTER);
		accountLabel.setText("΢���˺ţ�");
		accountText = new Text(r1, SWT.SINGLE);
		GridData gd_accountText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_accountText.widthHint = 90;
		accountText.setLayoutData(gd_accountText);
	    //��¼
		loginButton = new Button(r1,SWT.RIGHT);
	    GridData gd_loginButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
	    gd_loginButton.widthHint = 66;
	    loginButton.setLayoutData(gd_loginButton);
	    loginButton.setAlignment(SWT.CENTER);
	    
		Label passwordLabel = new Label(r1, SWT.CENTER);
		passwordLabel.setText("���룺");
		passwordText = new Text(r1, SWT.SINGLE);
		GridData gd_passwordText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_passwordText.widthHint = 90;
		passwordText.setLayoutData(gd_passwordText);
		passwordText.setEchoChar('*');
	    //�˳� 
		exitButton = new Button(r1,SWT.RIGHT);	     
	    GridData gd_exitButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
	    gd_exitButton.widthHint = 66;
	    exitButton.setLayoutData(gd_exitButton);
	    exitButton.setAlignment(SWT.CENTER);
	    exitButton.setText("ע��");
		
	    
		Group r2 = new Group(c, SWT.SHADOW_NONE);
		r2.setLayout(new GridLayout(1, true));
		
		Button keyBotton = new Button(r2, SWT.CHECK);
		keyBotton.setText("���ùؼ��֣�");

		Composite r21 = new Composite(r2, SWT.EMBEDDED);
		r21.setLayout(new RowLayout(SWT.HORIZONTAL));

		Label keyLabel = new Label(r21, SWT.CENTER);
		keyLabel.setText("�ؼ��֣�");
		keyText = new Text(r21, SWT.SINGLE);
		keyText.setLayoutData(new RowData(100, SWT.DEFAULT));
		keyText.setEditable(false);
		// ����Ĭ��ֵ
		accountText.setText("15189735221");  //����Ĭ���˺�
		passwordText.setText("qqaazz");//����Ĭ������
		keyText.setText("�� ����");           //����Ĭ�Ϲؼ���

		keyBotton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (((Button) e.widget).getSelection()) {
					keyText.setEditable(true);
					keyText.setText("");
				} else {
					keyText.setEditable(false);
					keyText.setText("�� ����");
				}
			}
		});

		Group r22 = new Group(c, SWT.SHADOW_NONE);
		r22.setLayout(new GridLayout(2, true));
		Button btnRadioButton1 = new Button(r22, SWT.RADIO);
		btnRadioButton1.setSelection(true);
		btnRadioButton1.setText("ʵʱ΢��");
		Button btnRadioButton2 = new Button(r22, SWT.RADIO);
		btnRadioButton2.setText("����΢��");
		Group r3 = new Group(c, SWT.SHADOW_NONE);
		r3.setLayout(new GridLayout(1, true));

		Button timeBotton = new Button(r3, SWT.CHECK);
		timeBotton.setText("����΢�������ʱ�䷶Χ��");
		timeBotton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (((Button) e.widget).getSelection()) {
					fromText.setEditable(true);
					toText.setEditable(true);
				} else {
					fromText.setText("");
					toText.setText("");
					fromText.setEditable(false);
					toText.setEditable(false);
				}
			}
		});

		Composite r31 = new Composite(r3, SWT.EMBEDDED);
		r31.setLayout(new RowLayout(SWT.HORIZONTAL));

		Label fromLabel = new Label(r31, SWT.CENTER);
		fromLabel.setText("��ʼʱ�䣺");
		fromText = new Text(r31, SWT.SINGLE);
		fromText.setEditable(false);

		Label toLabel = new Label(r31, SWT.CENTER);
		toLabel.setText("����ʱ�䣺");
		toText = new Text(r31, SWT.SINGLE);
		toText.setEditable(false);
		
		Label timeFormat = new Label(r3, SWT.CENTER);
		timeFormat.setText("ʱ���ʽ������2016��5��1��Ӧ����20160101");

		Group r4 = new Group(c, SWT.SHADOW_NONE);
		r4.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        r4.setLayout(new GridLayout(1,false));
        graspButton = new Button(r4, SWT.PUSH);                //ץȡ��ť
        GridData gd_openButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_openButton.widthHint = 80;
        graspButton.setLayoutData(gd_openButton);
        graspButton.setText("ץȡ");
		Label graspTip = new Label(r4, SWT.CENTER);
		graspTip.setText("Ϊ��ֹ�ļ����ǣ�һ������������ץȡ");
        
        progressBar = new ProgressBar(r4, SWT.NONE);          //������
        progressBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        consoleText = new Text(r4, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL);
        consoleText.setEditable(false);
        GridData gd_consoleText = new GridData(GridData.FILL_BOTH);
        gd_consoleText.heightHint = 80;
        consoleText.setLayoutData(gd_consoleText);
    
        //�жϵ�¼״̬  
        consoleText.insert("����¼״̬��,�糤ʱ������ʾ����������\n");
        loginButton.setText("��¼");
        loginButton.setEnabled(false);
    	exitButton.setEnabled(false);
    	graspButton.setEnabled(false);
    	new Thread(){   //Ϊ΢����¼��¼����һ���µ��߳�
            public void run(){           
            	task.checkLogin();
            }
        }.start(); 		
       //�س���Ӧ��ť
        getShell().setDefaultButton(loginButton);
        loginButton.addSelectionListener(new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent e) {	    		
	    		String username=accountText.getText();
                String password=passwordText.getText();                         
        		if(username.equals("")||password.equals(""))
        		{
        			consoleText.insert("�û����������벻��Ϊ��\n");
        		}
        		else
        		{
        			
        			consoleText.insert("���ڵ�¼...\n");
        		new Thread(){   //Ϊ΢����¼��¼����һ���µ��߳�
	                 public void run(){           
	                     task.login(username,password);               
	                 }
	             }.start(); 
        		}
	    	}
	    });
        
		 exitButton.addSelectionListener(new SelectionAdapter() {
		    	public void widgetSelected(SelectionEvent e) {
		    		task.logout();; //�ر������
		    		setButtonState(false);
		    		accountText.setText("");
		    		passwordText.setText("");
		    		consoleText.insert("��ע��.\n");
		    	}
		    });
		 
        graspButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                String keyword= keyText.getText().toString(); 
                String starttime=fromText.getText().toString();
                String endtime=toText.getText().toString();
                boolean sort = btnRadioButton1.getSelection();
            
                //����ץȡ΢�����ݱ���ĵ�ַ
                String path =workspace.getPath()
        				+ projDirectory.getPath() + WeiboConstants.WEIBO_SRC_DIR+"\\";          	
            	consoleText.insert("׼����......\n");
            	graspButton.setEnabled(false);   //��ץȡ��ť�����޷����״̬                   
        		new Thread(){   //Ϊ΢����¼��¼����һ���µ��߳�
	                 public void run(){           
	                    		try {               	
	                    			task.startGrasp(keyword,sort,starttime,endtime,path);
	                    		} catch (Exception e) {
									e.printStackTrace();
								} 
	                 }
	             }.start(); 		  
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
			super.buttonPressed(buttonId);

	}
	
	public void setButtonState(boolean bFlag){
        if(bFlag){ 
	    	loginButton.setText("�ѵ�¼");
	        loginButton.setEnabled(false);
	    	exitButton.setEnabled(true);
	    	graspButton.setEnabled(true); 
	    	accountText.setEditable(false);
	    	passwordText.setEditable(false);
	    	}
	    else { 
	        loginButton.setText("��¼");
	        loginButton.setEnabled(true);
	    	exitButton.setEnabled(false);
	    	graspButton.setEnabled(false); 
	    	accountText.setEditable(true);
	    	passwordText.setEditable(true);
	    }  
    }
	
	public void setGraspButtonState(boolean bFlag){
		graspButton.setEnabled(bFlag); 
	}
    public Text getConsoleText(){
        return consoleText;
    }
    public ProgressBar getProgressBar(){
        return progressBar;
    }
}