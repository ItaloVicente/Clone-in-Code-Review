package org.eclipse.egit.ui.internal.components;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SimplePushSpecPage extends WizardPage {

	private boolean forceUpdate;

	private Text remoteRefName;

	public SimplePushSpecPage(String niceSourceName) {
		super(UIText.SimplePushSpecPage_title);
		setTitle(UIText.SimplePushSpecPage_title);
		setMessage(NLS.bind(UIText.SimplePushSpecPage_message, niceSourceName));
	}

	public void createControl(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(main);
		main.setLayout(new GridLayout(1, false));

		Composite inputPanel = new Composite(main, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(inputPanel);
		inputPanel.setLayout(new GridLayout(2, false));

		final Label lblRemote = new Label(inputPanel, SWT.NONE);
		lblRemote.setText(UIText.SimplePushSpecPage_TargetRefName);
		remoteRefName = new Text(inputPanel, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(remoteRefName);
		remoteRefName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(isPageComplete());
			}
		});

		final Button forceButton = new Button(main, SWT.CHECK);
		forceButton.setText(UIText.RefSpecDialog_ForceUpdateCheckbox);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(forceButton);

		forceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				forceUpdate = forceButton.getSelection();
			}
		});

		setControl(main);
	}

	@Override
	public boolean isPageComplete() {
		return !remoteRefName.getText().isEmpty();
	}

	public boolean isForceUpdate() {
		return forceUpdate;
	}

	public String getTargetRef() {
		return remoteRefName.getText();
	}

}
