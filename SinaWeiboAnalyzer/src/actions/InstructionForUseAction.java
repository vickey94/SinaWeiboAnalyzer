package actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class InstructionForUseAction extends Action{
	
	public InstructionForUseAction(){
		super();
		setText("ʹ��˵��");
	}
	
	public void run(){
		Shell s=new Shell(Display.getCurrent(),SWT.CLOSE | SWT.MIN);
		s.setSize(610,500);
	
		s.setText("������������з�������ʹ��˵��");
		Text t=new Text(s,SWT.V_SCROLL|SWT.WRAP|SWT.READ_ONLY);
		t.setBackground(new Color(Display.getDefault(), new RGB(224,
				238, 224)));

	   String s1="1. �������\n������к�Ҫ��ѡ�����ռ䣬�����ռ��Ǳ�����Ŀ���ݵ��ļ���\n2. �½���Ŀ\n������򿪺󣬵���������ϵġ��½���Ŀ����ť������ѡ����Ŀ���˵��С���\n����Ŀ�����½���Ŀ���ɹ����������н�������Ŀ���ƺ���Ŀ·����ͬʱ������\n�ռ��г��ָմ�������Ŀ�ļ��У�\n3. ץȡ΢��\nѡ��ץȡ�ͼ��㡱�˵��С�ץȡ΢�������Ӳ˵������ߵ���������ϡ�ץȡ΢������ť���ڵ����Ĵ����У�������������΢���˺ź����룬Ĭ��ץȡ��΢���ؼ����ǣ�����  ���Ρ���Ĭ��ʱ��Ϊ����ǰʱ��Ϊֹ�������������������á����ú�����ץȡ����ť����ʵ��ץȡ���ܣ�ץȡ��ɺ������رմ��ڡ���ť��\n";
	  String s2="4. ��м���\nѡ��ץȡ�ͼ��㡱�˵��С���м�����������Ӳ˵������ߵ������������м������������ť���ڵ������ڣ�ѡ�񺬸�����дʵ��ı���м��㷽ʽ��Ĭ��Ϊֻ���㸺����дʵ��ۼӡ���������ʼ���㡱��ť���ɿ�ʼ��з�������з�����ɺ������رմ��ڡ���ť����м����Ԥ����Ϣ������ʾ����΢��Ԥ����Ϣ��\n";
       String s3="5. ����Ԥ����ֵ\n���������á��˵��С�����Ԥ����ֵ���˵���ڵ����ĶԻ����м������û�ɫԤ���ͺ�ɫԤ���ķ�Χ�����Ԥ����Ϣ���������ݣ������������õ���ֵ��ʾ��\n6.��ʾ����΢��\n���������á��˵��С���ʾ����΢��/����ʾ����΢�����Ӳ˵����ڵ����ĶԻ�����ѡ�С���ʾ����΢������ѡ��Ԥ����Ϣ����ʾ���е�΢����Ԥ����Ϣ��\n"  ;
	  String s4="7.��������ת��ΪXML��ʽ\n��Ŀ�½���ѡ�񡰱���ר�á��˵��С���������ת��ΪXML�����Ӳ˵������԰ѱ����õĲ����ı��ͱ�ע�ı�ת����xml�ĵ����洢����Ŀ�С�\n8. �����ı���м���\nѡ�񡰱���ר�á��˵��еġ������ı���м��㡰�Ӳ˵����ڵ������ڣ�ѡ�񺬸�����дʵ��ı���м��㷽ʽ��Ĭ��Ϊֻ���㸺����дʵ��ۼӡ��������ʼ���㡱��ť������������ݵ���з�ֵ����������󣬶Ի����²���ʾ����ʱ�䡢����΢���Ķ�ʧ�ʺ͸���΢���ı����ʡ��رնԻ���Ԥ����Ϣ�������ͼ����������еı�ע����ʾ�ı��ı�ע�����������ע��һ�µ��ı��Ժ�ɫ������ʾ��";
	  t.setText(s1+s2+s3+s4);
       t.setFont(new Font(null, "����", 12, SWT.NORMAL));
	 t.setSize(605, 450);
		s.setLocation(Display.getCurrent().getClientArea().width / 2 - s.getShell().getSize().x/2, Display.getCurrent()  
                .getClientArea().height / 2 - s.getSize().y/2); 
		s.open();
	}

}
