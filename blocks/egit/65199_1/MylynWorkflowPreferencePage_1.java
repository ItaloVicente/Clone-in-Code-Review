package org.eclipse.egit.internal.mylyn.ui.commit;

import org.eclipse.egit.ui.ICommitMessageEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class MylynTaskCommitMessageEditor extends Composite
		implements ICommitMessageEditor {

	public MylynTaskCommitMessageEditor(Composite parent, int style) {
		super(parent, style);

		RowLayout rowLayout = new RowLayout();
		rowLayout.spacing = 15;
		rowLayout.marginWidth = 15;
		rowLayout.marginHeight = 15;

		this.setLayout(rowLayout);

		Label label = new Label(this, SWT.NONE);
		label.setText("Demo implementation"); //$NON-NLS-1$
	}

	@Override
	public String getCommitMessage() {
		return ""; //$NON-NLS-1$
	}

	@Override
	public String getText() {
		return ""; //$NON-NLS-1$
	}

	@Override
	public void setText(String text) {

	}

	@Override
	public int getMinHeight() {
		return 20;
	}

	@Override
	public Control getCommentWidget() {
		return this;
	}

	@Override
	public void addVerifyKeyListener(VerifyKeyListener listener) {

	}

	@Override
	public void addValueChangeListener(
			CommitMessageChangeListener iCommitMessageChangeListener) {

	}

	@Override
	public Control getEditorWidget() {
		return this;
	}

}
