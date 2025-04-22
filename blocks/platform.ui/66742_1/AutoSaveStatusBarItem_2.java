package org.eclipse.ui.internal.ide.dialogs;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;

public class AutoSavePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private Button autoSaveButton;

	private Group autoSaveGroup;

	private Composite intervalComposite;

	private Label intervalMessageBegin;

	private Composite intervalFieldComposite;

	private IntegerFieldEditor intervalField;

	private Label intervalMessageEnd;

	private Label resetMessage;

	private Label noteMessage;

	private IPropertyChangeListener validityChangeListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(FieldEditor.IS_VALID)) {
				updateValidState();
			}
		}
	};

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = createComposite(parent);
		createAutoSaveCheckbox(composite);
		createAutoSaveGroup(composite);
		createIntervalPart();
		createMessagesPart();
		return composite;
	}

	@Override
	public void performDefaults() {
		boolean autoSave = getPreferenceStore().getDefaultBoolean(IPreferenceConstants.SAVE_AUTOMATICALLY);
		autoSaveButton.setSelection(autoSave);
		autoSaveButton.notifyListeners(SWT.Selection, new Event());
		int interval = getPreferenceStore().getDefaultInt(IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL);
		intervalField.setStringValue(String.valueOf(interval));
		intervalField.setEnabled(autoSave, intervalFieldComposite);

		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		if (isValid()) {
			getPreferenceStore().setValue(IPreferenceConstants.SAVE_AUTOMATICALLY, autoSaveButton.getSelection());
			getPreferenceStore().setValue(IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL,
					intervalField.getTextControl(intervalFieldComposite).getText());
		}
		return super.performOk();
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return WorkbenchPlugin.getDefault().getPreferenceStore();
	}

	@Override
	public void dispose() {
		intervalField.setPropertyChangeListener(null);
		noteMessage.dispose();
		resetMessage.dispose();
		intervalMessageEnd.dispose();
		intervalField.dispose();
		intervalMessageBegin.dispose();
		intervalFieldComposite.dispose();
		intervalComposite.dispose();
		autoSaveGroup.dispose();
		autoSaveButton.dispose();
		super.dispose();
	}

	protected boolean isAutoSaveButtonSelected() {
		final boolean enabled;
		if (autoSaveButton != null && !autoSaveButton.isDisposed()) {
			enabled = autoSaveButton.getSelection();
		} else {
			enabled = false;
		}
		return enabled;
	}

	protected void selectAutoSaveButton(boolean enable) {
		if (autoSaveButton != null && !autoSaveButton.isDisposed()) {
			autoSaveButton.setSelection(enable);
			autoSaveButton.notifyListeners(SWT.Selection, new Event());
		}
	}

	protected int getAutoSaveIntervalTextValue() {
		final int interval;
		if (intervalField != null && intervalFieldComposite != null
				&& !intervalField.getTextControl(intervalFieldComposite).isDisposed()) {
			interval = intervalField.getIntValue();
		} else {
			interval = 0;
		}
		return interval;
	}

	protected void setAutoSaveIntervalTextValue(int interval) {
		if (intervalField != null && intervalFieldComposite != null
				&& !intervalField.getTextControl(intervalFieldComposite).isDisposed() && autoSaveButton != null
				&& !autoSaveButton.isDisposed() && autoSaveButton.getSelection()) {
			intervalField.setStringValue(String.valueOf(interval));
		}
	}

	protected void updateValidState() {
		if (intervalField != null && !intervalField.isValid()) {
			setValid(false);
		} else {
			setValid(true);
		}

	}

	protected Composite createComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		return composite;
	}

	private void createAutoSaveCheckbox(Composite composite) {
		autoSaveButton = new Button(composite, SWT.CHECK);
		autoSaveButton.setText(IDEWorkbenchMessages.AutoSavePreferencPage_autoSaveButton);
		autoSaveButton.setToolTipText(IDEWorkbenchMessages.AutoSavePreferencPage_autoSaveButton);
		autoSaveButton.setSelection(getPreferenceStore().getBoolean(IPreferenceConstants.SAVE_AUTOMATICALLY));
		autoSaveButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean autoSave = autoSaveButton.getSelection();
				getPreferenceStore().setValue(IPreferenceConstants.SAVE_AUTOMATICALLY, autoSave);
				Display display = autoSaveButton.getDisplay();
				noteMessage.setEnabled(autoSave);
				resetMessage.setEnabled(autoSave);
				intervalMessageEnd.setEnabled(autoSave);
				intervalField.getTextControl(intervalFieldComposite).setEnabled(autoSave);
				intervalMessageBegin.setEnabled(autoSave);
				intervalFieldComposite.setEnabled(autoSave);
				intervalComposite.setEnabled(autoSave);
				autoSaveGroup.setEnabled(autoSave);
				if (autoSave) {
					noteMessage.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
					resetMessage.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
					intervalMessageEnd.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
					intervalMessageBegin.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
				} else {
					noteMessage.setForeground(display.getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
					resetMessage.setForeground(display.getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
					intervalMessageEnd.setForeground(display.getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
					intervalMessageBegin.setForeground(display.getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	private void createAutoSaveGroup(Composite composite) {
		autoSaveGroup = new Group(composite, SWT.NONE);
		GridLayout autoSaveGroupLayout = new GridLayout();
		autoSaveGroupLayout.numColumns = 1;
		autoSaveGroupLayout.marginWidth = 0;
		autoSaveGroupLayout.marginHeight = 0;
		autoSaveGroup.setLayout(autoSaveGroupLayout);
		GridData autoSaveGroupLayoutData = new GridData();
		autoSaveGroupLayoutData.horizontalAlignment = GridData.FILL;
		autoSaveGroupLayoutData.grabExcessHorizontalSpace = true;
		autoSaveGroup.setLayoutData(autoSaveGroupLayoutData);
		autoSaveGroup.setEnabled(autoSaveButton.getSelection());
	}

	private void createIntervalPart() {
		intervalComposite = new Composite(autoSaveGroup, SWT.NONE);
		GridLayout intervalCompositeLayout = new GridLayout();
		intervalCompositeLayout.numColumns = 3;
		intervalCompositeLayout.marginWidth = 0;
		intervalCompositeLayout.marginHeight = 10;
		intervalComposite.setLayout(intervalCompositeLayout);
		GridData intervalCompositeLayoutData = new GridData();
		intervalCompositeLayoutData.horizontalAlignment = GridData.FILL;
		intervalCompositeLayoutData.grabExcessHorizontalSpace = true;
		intervalComposite.setLayoutData(intervalCompositeLayoutData);
		intervalComposite.setEnabled(autoSaveButton.getSelection());

		intervalMessageBegin = new Label(intervalComposite, SWT.NONE);
		intervalMessageBegin.setText(IDEWorkbenchMessages.AutoSavePreferencPage_intervalMessageBegin);

		intervalFieldComposite = new Composite(intervalComposite, SWT.LEFT);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		intervalFieldComposite.setLayout(layout);
		intervalFieldComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		intervalField = new IntegerFieldEditor(IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL, "", //$NON-NLS-1$
				intervalFieldComposite);

		intervalField.setPreferenceStore(WorkbenchPlugin.getDefault().getPreferenceStore());
		intervalField.setPage(this);
		intervalField.setTextLimit(10);
		intervalField.setErrorMessage(IDEWorkbenchMessages.AutoSavePreferencPage_errorMessage);
		intervalField.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		intervalField.setValidRange(1, Integer.MAX_VALUE);
		intervalField.load();
		intervalField.getLabelControl(intervalFieldComposite).setEnabled(autoSaveButton.getSelection());
		intervalField.getTextControl(intervalFieldComposite).setEnabled(autoSaveButton.getSelection());
		intervalField.setPropertyChangeListener(validityChangeListener);

		intervalMessageEnd = new Label(intervalComposite, NONE);
		intervalMessageEnd.setText(IDEWorkbenchMessages.AutoSavePreferencPage_intervalMessageEnd);
	}

	private void createMessagesPart() {
		resetMessage = new Label(autoSaveGroup, NONE);
		resetMessage.setText(IDEWorkbenchMessages.AutoSavePreferencPage_resetMessage);

		noteMessage = new Label(autoSaveGroup, NONE);
		noteMessage.setText(
				IDEWorkbenchMessages.AutoSavePreferencPage_noteMessageBegin + System.getProperty("line.separator") //$NON-NLS-1$
						+ IDEWorkbenchMessages.AutoSavePreferencPage_noteMessageEnd);
	}

}
