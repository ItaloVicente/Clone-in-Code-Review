package org.eclipse.egit.ui;

import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

public interface ICommitMessageEditor {

	public String getCommitMessage();

	public String getText();

	public void setText(String text);

	public boolean setFocus();

	public boolean isEnabled();

	public int getMinHeight();

	public void setData(String key, Object value);

	public void setLayoutData(Object layoutData);

	public Point getSize();

	public Control getCommentWidget();

	public Control getEditorWidget();

	public void setEnabled(boolean enabled);

	public void addVerifyKeyListener(VerifyKeyListener listener);

	public void addCommitMessageChangeListener(
			CommitMessageChangeListener messageChangeListener);

	public interface CommitMessageChangeListener {

		public void commitMessageChanged();
	}
}
