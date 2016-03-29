/*
 * ShowAllWeiboDialog���ڶ���Ի����л���ʾ����΢�����ǽ���ʾ����΢��
 * 
 * */

package dialog;

import help.PathManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.*;

import root.AnalyzerWindow;

public class ShowAllWeiboDialog extends Dialog {
	AnalyzerWindow analyzerWindow;
	Button button;
	public ShowAllWeiboDialog(Shell parentShell, AnalyzerWindow analyzerWindow) {
		super(parentShell);
		this.analyzerWindow = analyzerWindow;
	}

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite) super.createDialogArea(parent);
		Label winLabel = new Label(comp, SWT.CENTER);
		winLabel.setText("��ʾ����΢��/����ʾ����΢��");
		Display disp = Display.getCurrent();
		FontData fontdata = new FontData("Arial", 11, SWT.BOLD);
		Font font = new Font(disp, fontdata);
		winLabel.setFont(font);

		button = new Button(comp, SWT.CHECK);
		button.setText("��ʾ����΢��");
		return comp;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, OK, "ȷ��", false);
		createButton(parent, CANCEL, "ȡ��", false);
		// createButton(parent, RESET_ID, "����", false);
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == OK) 
		{
			if (button.getSelection()) {
				TableViewer viewer = analyzerWindow.getViewer();
				viewer.removeFilter(analyzerWindow.getFilter());
				analyzerWindow.callInput(analyzerWindow.getWeibos());
			} else {
				TableViewer viewer = analyzerWindow.getViewer();
				ViewerFilter[] filters = viewer.getFilters();
				if (filters.length == 0) {
					viewer.addFilter(analyzerWindow.getFilter());
					analyzerWindow.callInput(analyzerWindow.getWeibos());
				}
			}

		} 
			super.buttonPressed(buttonId);
	}

}