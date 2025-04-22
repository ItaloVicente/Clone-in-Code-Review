package org.eclipse.egit.ui.internal.components;

import org.eclipse.core.runtime.Assert;
import org.eclipse.egit.ui.UIUtils;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.dialogs.AbstractBranchSelectionDialog;
import org.eclipse.egit.ui.internal.dialogs.LocalBranchSelectionDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class BranchSelectionComponent {

	private Repository repository;

	private Composite composite;

	private Label branchLabel;

	private Text branchText;

	private Button selectButton;

	public BranchSelectionComponent(final Composite parent, Repository repo) {
		Assert.isNotNull(repo);
		repository = repo;

		int numColumn = 3;

		composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		composite.setLayout(new GridLayout(numColumn, false));

		branchLabel = new Label(composite, SWT.NONE);
		branchLabel.setText(UIText.BranchSelectionComponent_label);

		branchText = new Text(composite, SWT.BORDER);
		GridData textData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textData.horizontalSpan = numColumn - 2;
		textData.horizontalIndent = 0;
		branchText.setLayoutData(textData);

		selectButton = new Button(composite, SWT.PUSH);
		selectButton.setText(UIText.BranchSelectionComponent_select);
		UIUtils.setButtonLayoutData(selectButton);
		selectButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				AbstractBranchSelectionDialog dialog = createDialog(
						parent.getShell(), branchText.getText());
				if (dialog.open() == Window.OK) {
					branchText.setText(Repository.shortenRefName(dialog
							.getRefName()));
				} else {
					branchText.setText(branchText.getText());
				}
			}
		});
	}

	public void setVisible(boolean visible) {
		composite.setVisible(visible);
		GridData gd = (GridData) composite.getLayoutData();
		gd.exclude = !visible;

	}

	public void setBranchName(String branchName) {
		branchText.setText(branchName);
	}

	public String getSelectedBranchName() {
		return branchText.getText();
	}

	public void addModifyListener(ModifyListener modifyListener) {
		branchText.addModifyListener(modifyListener);
	}

	protected AbstractBranchSelectionDialog createDialog(Shell shell,
			String branch) {
		return new LocalBranchSelectionDialog(shell, repository, branch);
	}
}
