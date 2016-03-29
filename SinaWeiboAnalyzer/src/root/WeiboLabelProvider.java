/*
 * ����Ԥ��΢�����LabelProvider
 * */

package root;

import help.WeiboConstants;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.jdom.Element;

public class WeiboLabelProvider implements ITableLabelProvider,
		ITableColorProvider, ITableFontProvider {
	int yellowAlarm;
	int redAlarm;
	int rowId;

	public WeiboLabelProvider(int yellowAlarm, int redAlarm) {
		super();
		this.yellowAlarm = yellowAlarm;
		this.redAlarm = redAlarm;
		rowId = 0;
	}

	public void setYellowAlarm(int yellowAlarm) {
		this.yellowAlarm = yellowAlarm;
	}

	public void setRedAlarm(int redAlarm) {
		this.redAlarm = redAlarm;
	}

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			rowId++;
			return Integer.toString(rowId);
		case 1:
			return ((Element) element).getChildText("�˺�");
		case 2:
			return ((Element) element).getChildText("����");
		case 3:
			return ((Element) element).getChildText("��ֵ");
		case 4:
			return ((Element) element).getChildText("ת��");
		case 5:
			return ((Element) element).getChildText("����");
		case 6:
			return ((Element) element).getChildText("����");
		case 7:
			return ((Element) element).getChildText("ʱ��");
		case 8:
			return ((Element) element).getChildText("��ע");
		case 9:
			return ((Element) element).getChildText("�ؼ���");

		}
		return "";
	}

	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public Color getBackground(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		String zfNumString = ((Element) element).getChildText("ת��");
		String plNumString = ((Element) element).getChildText("����");
		String dzNumString = ((Element) element).getChildText("����");
//		System.out.println(((Element) element).getChildText("ת��"));
		if (zfNumString != null && plNumString != null && dzNumString != null) {
			int total = Integer.parseInt(zfNumString.trim())
					+ Integer.parseInt(plNumString.trim())
					+ Integer.parseInt(dzNumString.trim());
			// ����Ԥ����ֵ����������ɫ
			if (total >= redAlarm) {
				return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
				// return new Color(Display.getDefault(),new RGB(255,99,71));
			} else if (total >= yellowAlarm) {
				// return
				// Display.getCurrent().getSystemColor(SWT.COLOR_DARK_YELLOW);
				return new Color(Display.getDefault(), new RGB(255, 165, 0));
			}
		}
		// ��������з����ע��һ�£���������Ϊ��ɫ

		String annotation = ((Element) element).getChildText("��ע");
		if (annotation != null) {
			boolean mistake = false;
			String score = ((Element) element).getChildText("��ֵ");
			annotation = annotation.trim();
			int score_int = Integer.parseInt(score.trim());
			if(annotation.equals(WeiboConstants.WORD_FOR_NEUTRAL) && score_int!=0)
				mistake = true;
			else if(annotation.equals(WeiboConstants.WORD_FOR_NEGATIVE) && !(score_int<0))
				mistake = true;
			else if(annotation.equals(WeiboConstants.WORD_FOR_POSITIVE) && !(score_int>0))
			    mistake = true;
			if (mistake)
				return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		}

		return null;
	}

	@Override
	public Font getFont(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

}
