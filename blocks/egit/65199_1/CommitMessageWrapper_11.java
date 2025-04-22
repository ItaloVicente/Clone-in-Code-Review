package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

public interface CommitMessageEditor {

	public String getCommitMessage();

	public String getText();

	public void setText(String text);



	public boolean setFocus();


	public boolean isEnabled();



	public int getMinHeight();

	public void setData(String key, Object value);

	public void setLayoutData(Object layoutData);

	public Point getSize();

	public Control getWidget();

	public void setEnabled(boolean enabled);


	public void addVerifyKeyListener(VerifyKeyListener listener);

	public void addValueChangeListener(
			CommitMessageChangeListener iCommitMessageChangeListener);

	public interface CommitMessageChangeListener

	{

		public void commitMessageChanged();
	}
}
