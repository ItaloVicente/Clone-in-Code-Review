package org.eclipse.egit.ui.internal.clean;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class CleanProjectsConfirmDialog extends TitleAreaDialog {

	private boolean shouldDryRun;
	private boolean shouldCleanDirs;

	public CleanProjectsConfirmDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();

		setTitle(UIText.CleanProjectsConfirmDialog_title);
		setMessage(UIText.CleanProjectsConfirmDialog_message);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(main);

		main.setLayout(new GridLayout(1, false));

		final Button dryRun = new Button(main, SWT.CHECK);
		final Button cleanDirs = new Button(main, SWT.CHECK);

		dryRun.setText(UIText.CleanProjectsConfirmDialog_dryRun);
		cleanDirs.setText(UIText.CleanProjectsConfirmDialog_cleanDirs);

		dryRun.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				shouldDryRun = dryRun.getSelection();
			}

		});

		cleanDirs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shouldCleanDirs = cleanDirs.getSelection();
			}
		});
		return main;
	}

	public boolean shouldDryRun() {
		return shouldDryRun;
	}

	public boolean shouldCleanDirectories() {
		return shouldCleanDirs;
	}

}
