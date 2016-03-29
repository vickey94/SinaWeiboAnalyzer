/*
 * ��������ڵ���SinaWeibo������ʵ��΢��ץȡ
 */
package grasp;

import org.eclipse.swt.widgets.Display;


import dialog.GraspWeiboDialog;

public class GraspProcess extends Thread {
	private GraspWeiboDialog guipr; // ǰ̨�������

	/**ȡ��ǰ̨����**/
	public GraspProcess(GraspWeiboDialog taskGUI) {
		this.guipr = taskGUI;
	}
	
	/**��¼΢���˺�**/
	public void login(String username ,String password){		
		String loginvalidator =SinaWeibo.login(username, password);
		intsertConsoleText(loginvalidator);
		if(loginvalidator.equals("��½�ɹ���\n")){
			setGraspGUIButtonState(true);
		}
	}
	
	/**�˳�΢���˺�**/
	public void logout(){
		SinaWeibo.close();	
	}
	
	/**����Ƿ��¼����΢���˺�**/
	public void checkLogin(){
		boolean flag;
		try {
			flag = SinaWeibo.whetherLogin();
			setGraspGUIButtonState(flag);
			if(!flag) intsertConsoleText("���¼\n"); 
			else intsertConsoleText("�ѵ�¼�����Խ���ץȡ\n"); 
		} catch (Exception e) {
			intsertConsoleText("�������磡\n");
		}

	}
	
	/**�����������ץȡ΢��������΢�� ���������ؼ��֡�΢�����͡���ʼʱ�䡢����ʱ�䡢�����ַ	**/
	public void startGrasp(String keyword,boolean sort,
			String starttime, String endtime, String path){
		String url = null;
		int page = 0; // ��ҳ��ҳ��
		int countpage = 0; // ��ǰҳ��
		SinaWeibo.wbcount = 0; // ��������
		// ��¼΢�������ص�¼��� 
			url = SinaWeibo.URLEncode(keyword,sort,starttime, endtime); // ��ù������ַ
			intsertConsoleText("��ʼ������\n");
			page = SinaWeibo.getPage(url + "1"); // ��ù����ж���ҳ΢��
			maxProgressBar(page - 1); // ���ý��������ֵ
			
			// ���ѭ������ץȡÿҳ΢��
			for (int i = 1; i <= page; i++) {
					intsertConsoleText(SinaWeibo.catchWeibo(url, path, i)
							+ "\n");
					countpage++;
				moveProgressBar(i);// ������+1
			}
			intsertConsoleText("��ץȡ" + SinaWeibo.wbcount + "��΢��,����" + countpage
					+ "��xml�ļ�!\n");
			 setGraspGUIGraspButtonState(true);//ˢ�½��桰ץȡ����ť״̬
	}

	/**���ý��水ť״̬**/
	private void setGraspGUIButtonState(final boolean bFlag) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				guipr.setButtonState(bFlag);
			}
		});
	}
	
    /**���ý��桰ץȡ����ť״̬**/
	private void setGraspGUIGraspButtonState(final boolean bFlag) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				guipr.setGraspButtonState(bFlag);
			}
		});
	}
	
	/**�ƶ�������**/
	private void moveProgressBar(final int progress) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				guipr.getProgressBar().setSelection(progress);
			}
		});
	}

	/**��������������ý��������ֵ**/	 
	private void maxProgressBar(final int progress) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				guipr.getProgressBar().setMaximum(progress);
				;

			}
		});
	}

	/**�������������Ϣ����ʾ��Ϣ**/
	private void intsertConsoleText(final String str) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				guipr.getConsoleText().insert(str);

			}
		});
	}
}