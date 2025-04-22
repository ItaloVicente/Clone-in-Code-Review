package org.eclipse.ui.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivity;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.IActivityRequirementBinding;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.activities.NotDefinedException;

final class ActivityPersistanceHelper {

    protected final static String PREFIX = "UIActivities."; //$NON-NLS-1$    

    private static ActivityPersistanceHelper singleton;

    private final IActivityManagerListener activityManagerListener = new IActivityManagerListener() {

        @Override
		public void activityManagerChanged(
                ActivityManagerEvent activityManagerEvent) {
            if (activityManagerEvent.haveDefinedActivityIdsChanged()) {
                Set delta = new HashSet(activityManagerEvent
                        .getActivityManager().getDefinedActivityIds());
                delta.removeAll(activityManagerEvent
                        .getPreviouslyDefinedActivityIds());
                loadEnabledStates(activityManagerEvent
                        .getActivityManager().getEnabledActivityIds(), delta);
            }
            if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
				saveEnabledStates();
			}
        }
    };
    
    private final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {

        @Override
		public void propertyChange(PropertyChangeEvent event) {
            if (!saving && event.getProperty().startsWith(PREFIX)) {
                String activityId = event.getProperty().substring(PREFIX.length());
                IWorkbenchActivitySupport support = PlatformUI.getWorkbench().getActivitySupport();
                IActivityManager activityManager = support.getActivityManager();
                
                boolean enabled = Boolean.valueOf(event.getNewValue().toString()).booleanValue();
                Set set = new HashSet(activityManager.getEnabledActivityIds());
                if (enabled == false) {
                    Set dependencies = buildDependencies(activityManager, activityId);
                    set.removeAll(dependencies);
                }
                else {
                    set.add(activityId);
                }
                support.setEnabledActivityIds(set);
            }
        }
    };

    protected boolean saving = false;

    public static ActivityPersistanceHelper getInstance() {
        if (singleton == null) {
            singleton = new ActivityPersistanceHelper();
        }
        return singleton;
    }

    protected Set buildDependencies(IActivityManager activityManager, String activityId) {
        Set set = new HashSet();
        for (Iterator i = activityManager.getDefinedActivityIds().iterator(); i.hasNext(); ) {
            IActivity activity = activityManager.getActivity((String) i.next());
            for (Iterator j = activity.getActivityRequirementBindings().iterator(); j.hasNext(); ) {
                IActivityRequirementBinding binding = (IActivityRequirementBinding) j.next();
                if (activityId.equals(binding.getRequiredActivityId())) {
                    set.addAll(buildDependencies(activityManager, activity.getId()));
                }
            }
        }
        set.add(activityId);
        return set;
    }

    private ActivityPersistanceHelper() {
        loadEnabledStates();
        hookListeners();
    }

    private void hookListeners() {
        IWorkbenchActivitySupport support = PlatformUI.getWorkbench()
                .getActivitySupport();

        IActivityManager activityManager = support.getActivityManager();

        activityManager.addActivityManagerListener(activityManagerListener);

        IPreferenceStore store = WorkbenchPlugin.getDefault()
                .getPreferenceStore();
        
        store.addPropertyChangeListener(propertyChangeListener);        
    }

    private void unhookListeners() {
        IWorkbenchActivitySupport support = PlatformUI.getWorkbench()
                .getActivitySupport();

        IActivityManager activityManager = support.getActivityManager();

        activityManager.removeActivityManagerListener(activityManagerListener); 
        
        IPreferenceStore store = WorkbenchPlugin.getDefault()
                .getPreferenceStore();
        
        store.removePropertyChangeListener(propertyChangeListener);                
    }
    
    private String createPreferenceKey(String activityId) {
        return PREFIX + activityId;
    }

    void loadEnabledStates() {
        loadEnabledStates(Collections.EMPTY_SET, PlatformUI.getWorkbench()
                .getActivitySupport().getActivityManager()
                .getDefinedActivityIds());
    }

    protected void loadEnabledStates(Set previouslyEnabledActivities, Set activityIdsToProcess) {
        if (activityIdsToProcess.isEmpty()) {
			return;
		}
        
        Set enabledActivities = new HashSet(previouslyEnabledActivities);
        IPreferenceStore store = WorkbenchPlugin.getDefault()
                .getPreferenceStore();

        IWorkbenchActivitySupport support = PlatformUI.getWorkbench()
                .getActivitySupport();

        IActivityManager activityManager = support.getActivityManager();

        for (Iterator i = activityIdsToProcess.iterator(); i
                .hasNext();) {
            String activityId = (String) i.next();
            String preferenceKey = createPreferenceKey(activityId);
			try {
				IActivity activity = activityManager.getActivity(activityId);
				if (activity.getExpression() != null) {
					continue;
				}
                if ("".equals(store.getDefaultString(preferenceKey))) { //$NON-NLS-1$ // no override has been provided in the customization file
                	store // the default should be whatever the XML specifies
					.setDefault(preferenceKey, activity
							.isDefaultEnabled());
                	
                }				

            } catch (NotDefinedException e) {
            }

            if (store.getBoolean(preferenceKey)) {
				enabledActivities.add(activityId);
			} else {
				enabledActivities.remove(activityId);
			}
        }

        support.setEnabledActivityIds(enabledActivities);
    }

    protected void saveEnabledStates() {
        try {
            saving = true;
	        
	        IPreferenceStore store = WorkbenchPlugin.getDefault()
	                .getPreferenceStore();
	
	        IWorkbenchActivitySupport support = PlatformUI.getWorkbench()
	                .getActivitySupport();
	        IActivityManager activityManager = support.getActivityManager();
	        Iterator values = activityManager.getDefinedActivityIds().iterator();
	        while (values.hasNext()) {
	            IActivity activity = activityManager.getActivity((String) values
	                    .next());
	            if (activity.getExpression() != null) {
	            	continue;
	            }
	
	            store.setValue(createPreferenceKey(activity.getId()), activity
	                    .isEnabled());
	        }
	        WorkbenchPlugin.getDefault().savePluginPreferences();
        }
        finally {
            saving = false;
        }
    }

    public void shutdown() {
        unhookListeners();
        saveEnabledStates();        
    }
}
