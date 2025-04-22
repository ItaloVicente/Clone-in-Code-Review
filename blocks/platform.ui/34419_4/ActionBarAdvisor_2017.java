package org.eclipse.ui.activities;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.activities.ws.EnablementDialog;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.internal.util.Util;

public class WorkbenchTriggerPointAdvisor implements ITriggerPointAdvisor,
        IExecutableExtension {

	public static String PROCEED_MULTI = "proceedMulti"; //$NON-NLS-1$
	
	public static String PROCEED_SINGLE = "proceedSingle"; //$NON-NLS-1$
	
	public static String DONT_ASK = "dontAsk"; //$NON-NLS-1$
	
	public static String NO_DETAILS = "noDetails"; //$NON-NLS-1$

	
	private Properties strings = new Properties();
	
	public WorkbenchTriggerPointAdvisor() {
		super();
	}

	@Override
	public Set allow(ITriggerPoint triggerPoint, IIdentifier identifier) {
		
		if (triggerPoint.getBooleanHint(ITriggerPoint.HINT_PRE_UI)) {
			IActivityManager activityManager = PlatformUI.getWorkbench()
					.getActivitySupport().getActivityManager();
			Iterator iterator = identifier.getActivityIds().iterator();
			while (iterator.hasNext()) {
				String id = (String) iterator.next();
				IActivity activity = activityManager.getActivity(id);
				if (activity.getExpression() != null) {
					if (!activity.isEnabled())
						return null; 
				}
			}
			return Collections.EMPTY_SET;  
		}
		
        if (!PrefUtil.getInternalPreferenceStore().getBoolean(
                IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT)) {
            return identifier.getActivityIds();
        }		
		
		if (!triggerPoint.getBooleanHint(ITriggerPoint.HINT_INTERACTIVE)) {
			return identifier.getActivityIds();
		}
		
        EnablementDialog dialog = new EnablementDialog(Util.getShellToParentOn(), identifier
                .getActivityIds(), strings);
        if (dialog.open() == Window.OK) {
            Set activities = dialog.getActivitiesToEnable();
            if (dialog.getDontAsk()) {
				PrefUtil.getInternalPreferenceStore().setValue(
						IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT,
						false);
				WorkbenchPlugin.getDefault().savePluginPreferences();
			}

            return activities;
        }
		
		return null;
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) {
		if (data instanceof Hashtable) {
			strings.putAll((Hashtable)data);
		}		
	}

	@Override
	public boolean computeEnablement(IActivityManager activityManager, IIdentifier identifier) {
		return doComputeEnablement(activityManager, identifier, false);
	}

	protected boolean doComputeEnablement(IActivityManager activityManager,
			IIdentifier identifier, boolean disabledExpressionActivitiesTakePrecedence) {
		final Set activityIds = identifier.getActivityIds();
		if (activityIds.size() == 0) {
			return true;
		}

		boolean matchesAtLeastOneEnabled = false;
		boolean matchesDisabledExpressionActivitiesWithPrecedence = false;
		for (Iterator iterator = activityIds.iterator(); iterator.hasNext();) {
			String activityId = (String) iterator.next();
			IActivity activity = activityManager.getActivity(activityId);
			
			if (activity.isEnabled()) {
				if (!disabledExpressionActivitiesTakePrecedence) {
					return true;
				}
				matchesAtLeastOneEnabled = true;
			} else {
				if (disabledExpressionActivitiesTakePrecedence && activity.getExpression() != null) {
					matchesDisabledExpressionActivitiesWithPrecedence = true;
				}
			}

		}

		return !matchesDisabledExpressionActivitiesWithPrecedence && matchesAtLeastOneEnabled;
	}
}
