package org.eclipse.egit.gitflow.ui.internal.dialogs;

import org.eclipse.egit.gitflow.ui.internal.UIText;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class FinishFeatureDialog extends TitleAreaDialog {

	private boolean squash;

	private Button squashButton;

	private String featureBranch;

	public FinishFeatureDialog(Shell parentShell, String featureBranch) {
		super(parentShell);
		this.featureBranch = featureBranch;
	}

	@Override
	public void create() {
		super.create();
		setTitle(UIText.FinishFeatureDialog_title);
		setMessage(NLS.bind(
				UIText.FinishFeatureDialog_setParameterForFinishing,
				featureBranch), IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(1, false);
		container.setLayout(layout);

		squashButton = new Button(container, SWT.CHECK);
		squashButton.setText(UIText.FinishFeatureDialog_squashCheck);

		return area;
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		return super.createButtonBar(parent);
	}

	@Override
	public boolean isHelpAvailable() {
		return false;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void saveInput() {
		this.squash = squashButton.getSelection();

	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public boolean isSquash() {
		return squash;
	}
}
