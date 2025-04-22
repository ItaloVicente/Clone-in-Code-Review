/*******************************************************************************
 * Copyright (C) 2014, 2016 Google Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Marcus Eng (Google) - initial API and implementation
 *     Sergey Prigogin (Google)
 *******************************************************************************/
package org.eclipse.ui.internal.monitoring.preferences;

import java.util.Arrays;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

/**
 * Displays the list of stack frames used as a filter.
 */
public class FilterListEditor extends ListEditor {
	private String dialogMessage;

	FilterListEditor(String name, String label, String addButtonLabel, String removeButtonLabel,
			String dialogMessage, Composite parent) {
		super(name, label, parent);
		this.dialogMessage = dialogMessage;
		setButtonLabel(getAddButton(), addButtonLabel);
		setButtonLabel(getRemoveButton(), removeButtonLabel);
		getUpButton().setVisible(false);
		getDownButton().setVisible(false);
	}

	private void setButtonLabel(Button button, String label) {
		button.setText(label);
		GridDataFactory.fillDefaults().applyTo(button);
	}

    @Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
    	super.doFillIntoGrid(parent, numColumns);
        List list = getListControl(parent);
        GridDataFactory.defaultsFor(list).applyTo(list);
        GridDataFactory.fillDefaults().applyTo(getButtonBoxControl(parent));
    }

	/**
	 * Handles parsing of defined traces to be filtered.
	 */
	@Override
	protected String createList(String[] items) {
		StringBuilder mergedItems = new StringBuilder();

		for (String item : items) {
			item = item.trim();
			if (mergedItems.length() != 0) {
				mergedItems.append(',');
			}
			mergedItems.append(item);
		}

		return mergedItems.toString();
	}

	@Override
	protected String getNewInputObject() {
		FilterInputDialog dialog = new FilterInputDialog(getShell(), dialogMessage);
		if (dialog.open() == Window.OK) {
			String filter = dialog.getFilter();
			List list = getList();
			if (list.getItemCount() != 0) {
				int pos = Arrays.binarySearch(list.getItems(), filter);
				if (pos >= 0) {
				}
				list.setSelection(-pos - 2);
			}
			return filter;
		}
		return null;
	}

	@Override
	protected String[] parseString(String stringList) {
		if (stringList.isEmpty()) {
			return new String[0];
		}
		String[] items = stringList.split(",); //$NON-NLS-1$
-		Arrays.sort(items);;
-		return items;
-	}
-
-	@Override
-	protected void refreshValidState() {
-		selectionChanged();
-	}
-}
diff --git a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/Messages.java b/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/Messages.java
deleted file mode 100644
index 0e51614f89..0000000000
--- a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/Messages.java
+++ /dev/null
@@ -1,48 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2014 Google, Inc and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *	   Marcus Eng (Google) - initial API and implementation
- *	   Sergey Prigogin (Google)
- *******************************************************************************/
-package org.eclipse.ui.internal.monitoring.preferences;
-
-import org.eclipse.osgi.util.NLS;
-
-public final class Messages extends NLS {
-	public static String FilterInputDialog_filter_input_label;
-	public static String FilterInputDialog_header;
-	public static String FilterInputDialog_invalid_method_name;
-	public static String FilterInputDialog_noninteresting_thread_filter_message;
-	public static String FilterInputDialog_note_label;
-	public static String FilterInputDialog_title;
-	public static String FilterInputDialog_ui_thread_filter_message;
-	public static String MonitoringPreferenceListener_preference_error_header;
-	public static String MonitoringPreferenceListener_preference_error;
-	public static String MonitoringPreferencePage_add_ui_thread_filter_button_label;
-	public static String MonitoringPreferencePage_add_noninteresting_thread_filter_button_label;
-	public static String MonitoringPreferencePage_deadlock_threshold_label;
-	public static String MonitoringPreferencePage_deadlock_threshold_too_low_error;
-	public static String MonitoringPreferencePage_enable_monitoring_label;
-	public static String MonitoringPreferencePage_error_threshold_label;
-	public static String MonitoringPreferencePage_error_threshold_too_low_error;
-	public static String MonitoringPreferencePage_log_freeze_events_label;
-	public static String MonitoringPreferencePage_max_stack_samples_label;
-	public static String MonitoringPreferencePage_noninteresting_thread_filter_label;
-	public static String MonitoringPreferencePage_remove_ui_thread_filter_button_label;
-	public static String MonitoringPreferencePage_remove_noninteresting_thread_filter_button_label;
-	public static String MonitoringPreferencePage_ui_thread_filter_label;
-	public static String MonitoringPreferencePage_warning_threshold_label;
-
-	private Messages() {
-		// Do not instantiate.
-	}
-
-	static {
-		NLS.initializeMessages(Messages.class.getName(), Messages.class);
-	}
-}
diff --git a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/Messages.properties b/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/Messages.properties
deleted file mode 100644
index e649ec614f..0000000000
--- a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/Messages.properties
+++ /dev/null
@@ -1,36 +0,0 @@
-###############################################################################
-# Copyright (c) 2014, 2015 Google, Inc and others.
-# All rights reserved. This program and the accompanying materials
-# are made available under the terms of the Eclipse Public License v1.0
-# which accompanies this distribution, and is available at
-# http://www.eclipse.org/legal/epl-v10.html
-#
-# Contributors:
-#	  Marcus Eng (Google) - initial API and implementation
-#	  Sergey Prigogin (Google)
-#	  IBM Corporation - bug fixes
-###############################################################################
-
-FilterInputDialog_filter_input_label=Stack &frame:
-FilterInputDialog_header=New Stack Trace Filter
-FilterInputDialog_invalid_method_name=Invalid method name
-FilterInputDialog_noninteresting_thread_filter_message=Enter the fully qualified method name of a stack frame. A non-UI thread is excluded from the logged message if all its stack frames match the filter.
-FilterInputDialog_note_label=(* = any string, ? = any character)
-FilterInputDialog_title=Add Filter
-FilterInputDialog_ui_thread_filter_message=Enter the fully qualified method name of a stack frame. UI freezes containing this method in a stack trace of the UI thread are ignored.
-MonitoringPreferenceListener_preference_error_header=Invalid Preferences
-MonitoringPreferenceListener_preference_error=The specified preferences could not be updated. See error log for details.
-MonitoringPreferencePage_add_ui_thread_filter_button_label=Add &Filter...
-MonitoringPreferencePage_add_noninteresting_thread_filter_button_label=Add F&ilter...
-MonitoringPreferencePage_deadlock_threshold_label=Deadl&ock threshold (ms):
-MonitoringPreferencePage_deadlock_threshold_too_low_error=The deadlock threshold must be higher than the error threshold.
-MonitoringPreferencePage_enable_monitoring_label=De&tect periods of unresponsive UI
-MonitoringPreferencePage_error_threshold_label=&Error threshold (ms):
-MonitoringPreferencePage_error_threshold_too_low_error=The error threshold cannot be lower than the warning threshold.
-MonitoringPreferencePage_log_freeze_events_label=&Log UI freezes to Eclipse error log
-MonitoringPreferencePage_max_stack_samples_label=&Maximum stack samples to log:
-MonitoringPreferencePage_noninteresting_thread_filter_label=E&xclude a non-UI thread from the logged message if all its stack frames match the filter:
-MonitoringPreferencePage_remove_ui_thread_filter_button_label=&Remove
-MonitoringPreferencePage_remove_noninteresting_thread_filter_button_label=Remo&ve
-MonitoringPreferencePage_ui_thread_filter_label=Ig&nore a UI freeze if a stack trace of the UI thread contains at least one frame matching the filter:
-MonitoringPreferencePage_warning_threshold_label=&Warning threshold (ms):
diff --git a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferenceInitializer.java b/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferenceInitializer.java
deleted file mode 100644
index ba5235849e..0000000000
--- a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferenceInitializer.java
+++ /dev/null
@@ -1,46 +0,0 @@
-/*******************************************************************************
- * Copyright (C) 2014 Google Inc and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     Marcus Eng (Google) - initial API and implementation
- *     Sergey Prigogin (Google)
- *******************************************************************************/
-package org.eclipse.ui.internal.monitoring.preferences;
-
-import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
-import org.eclipse.jface.preference.IPreferenceStore;
-import org.eclipse.ui.internal.monitoring.MonitoringPlugin;
-import org.eclipse.ui.monitoring.PreferenceConstants;
-
-/**
- * Initializes the default values of the monitoring plug-in preferences.
- */
-public class MonitoringPreferenceInitializer extends AbstractPreferenceInitializer {
-	@Override
-	public void initializeDefaultPreferences() {
-		IPreferenceStore store = MonitoringPlugin.getDefault().getPreferenceStore();
-
-		store.setDefault(PreferenceConstants.MONITORING_ENABLED, false);
-		store.setDefault(PreferenceConstants.LONG_EVENT_WARNING_THRESHOLD_MILLIS, 500); // 0.5 sec
-		store.setDefault(PreferenceConstants.LONG_EVENT_ERROR_THRESHOLD_MILLIS, 2000); // 2 sec
-		store.setDefault(PreferenceConstants.MAX_STACK_SAMPLES, 3);
-		store.setDefault(PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS,
-				5 * 60 * 1000); // 5 min
-		store.setDefault(PreferenceConstants.LOG_TO_ERROR_LOG, true);
-		store.setDefault(PreferenceConstants.UI_THREAD_FILTER, "); //$NON-NLS-1$
-		store.setDefault(PreferenceConstants.NONINTERESTING_THREAD_FILTER,
-				java.*" //$NON-NLS-1$
				+ ",sun.*" //$NON-NLS-1$
				+ ",org.eclipse.core.internal.jobs.WorkerPool.sleep" //$NON-NLS-1$
				+ ",org.eclipse.core.internal.jobs.WorkerPool.startJob" //$NON-NLS-1$
				+ ",org.eclipse.core.internal.jobs.Worker.run" //$NON-NLS-1$
				+ ",org.eclipse.osgi.framework.eventmgr.EventManager$EventThread.getNextEvent" //$NON-NLS-1$
				+ ",org.eclipse.osgi.framework.eventmgr.EventManager$EventThread.run" //$NON-NLS-1$
				+ ",org.eclipse.equinox.internal.util.impl.tpt.timer.TimerImpl.run" //$NON-NLS-1$
				+ ",org.eclipse.equinox.internal.util.impl.tpt.threadpool.Executor.run"); //$NON-NLS-1$
	}
}
