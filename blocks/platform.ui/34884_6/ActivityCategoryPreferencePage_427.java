package org.eclipse.ui.activities;

import java.util.Hashtable;
import java.util.Properties;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.activities.ws.ActivityEnabler;
import org.eclipse.ui.internal.activities.ws.ActivityMessages;

public final class ActivitiesPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage, IExecutableExtension {

    public static final String ACTIVITY_NAME = "activityName"; //$NON-NLS-1$
    
    public static final String ACTIVITY_PROMPT_BUTTON = "activityPromptButton"; //$NON-NLS-1$
    
    public static final String ACTIVITY_PROMPT_BUTTON_TOOLTIP = "activityPromptButtonTooltip"; //$NON-NLS-1$
    
	private Button activityPromptButton;

    private IWorkbench workbench;

    private ActivityEnabler enabler;
    
    private Properties strings = new Properties();

    private IMutableActivityManager workingCopy;
    
    protected void createActivityPromptPref(Composite composite) {
        activityPromptButton = new Button(composite, SWT.CHECK);
        activityPromptButton.setText(strings.getProperty(ACTIVITY_PROMPT_BUTTON, ActivityMessages.activityPromptButton)); 
        activityPromptButton.setToolTipText(strings.getProperty(ACTIVITY_PROMPT_BUTTON_TOOLTIP, ActivityMessages.activityPromptToolTip));

        setActivityButtonState();
    }

    private void setActivityButtonState() {
        activityPromptButton.setSelection(getPreferenceStore().getBoolean(
                IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT));
    }

    @Override
	protected Control createContents(Composite parent) {
    	initializeDialogUnits(parent);
    	
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        composite.setLayout(layout);

        createActivityPromptPref(composite);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        activityPromptButton.setLayoutData(data);

        data = new GridData(GridData.FILL_BOTH);
        workingCopy = workbench.getActivitySupport().createWorkingCopy();
        enabler = new ActivityEnabler(workingCopy, strings);
        enabler.createControl(composite).setLayoutData(data);
        
        Dialog.applyDialogFont(composite);

        return composite;
    }

    @Override
	public void init(IWorkbench aWorkbench) {
        this.workbench = aWorkbench;
        setPreferenceStore(WorkbenchPlugin.getDefault().getPreferenceStore());
    }

    @Override
	public boolean performOk() {
        enabler.updateActivityStates();
        workbench.getActivitySupport().setEnabledActivityIds(workingCopy.getEnabledActivityIds());
        
        getPreferenceStore().setValue(
                IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT,
                activityPromptButton.getSelection());

        return true;
    }

    @Override
	protected void performDefaults() {
        enabler.restoreDefaults();
        activityPromptButton.setSelection(getPreferenceStore()
                .getDefaultBoolean(
                        IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT));
        super.performDefaults();
    }

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) {
		if (data instanceof Hashtable) {
			strings.putAll((Hashtable)data);
		}		
	}
}
