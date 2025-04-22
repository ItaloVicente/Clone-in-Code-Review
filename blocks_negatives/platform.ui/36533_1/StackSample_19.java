/*******************************************************************************
 * Copyright (C) 2014, Google Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Marcus Eng (Google) - initial API and implementation
 *     Sergey Prigogin (Google)
 *******************************************************************************/
package org.eclipse.ui.internal.monitoring.preferences;

import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

/**
 * Displays the list of traces to filter out and ignore.
 */
public class FilterListEditor extends ListEditor {
	FilterListEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
		getAddButton().setText(Messages.ListFieldEditor_add_filter_button_label);
		getUpButton().setVisible(false);
		getDownButton().setVisible(false);
	}

    @Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
    	super.doFillIntoGrid(parent, numColumns);
        List list = getListControl(parent);
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, numColumns - 1, 1);
    	PixelConverter pixelConverter = new PixelConverter(parent);
        gd.widthHint = pixelConverter.convertWidthInCharsToPixels(65);
        list.setLayoutData(gd);
    }

	/**
	 * Handles parsing of defined traces to be filtered.
	 */
	@Override
	protected String createList(String[] items) {
		StringBuilder mergedItems = new StringBuilder();

		for (String item : items) {
			item.trim();
			if (mergedItems.length() != 0) {
				mergedItems.append(',');
			}
			mergedItems.append(item);
		}

		return mergedItems.toString();
	}

	@Override
	protected String getNewInputObject() {
		FilterInputDialog dialog = new FilterInputDialog(getShell());
		if (dialog.open() == Window.OK) {
			return dialog.getFilter();
		}
		return null;
	}

	@Override
	protected String[] parseString(String stringList) {
		if (stringList.isEmpty()) {
			return new String[0];
		}
		return stringList.split(",); //$NON-NLS-1$
-	}
-
-	@Override
-	protected void refreshValidState() {
-		selectionChanged();
-	}
-}
diff --git a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/Messages.java b/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/Messages.java
deleted file mode 100644
index 61cf4aea01..0000000000
--- a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/Messages.java
+++ /dev/null
@@ -1,42 +0,0 @@
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
-	public static String FilterInputDialog_filter_input_header;
-	public static String FilterInputDialog_filter_input_message;
-	public static String FilterInputDialog_filter_input_label;
-	public static String FilterInputDialog_filter_input_title;
-	public static String FilterInputDialog_invalid_method_name;
-	public static String ListFieldEditor_add_filter_button_label;
-	public static String MonitoringPreferenceListener_preference_error_header;
-	public static String MonitoringPreferenceListener_preference_error;
-	public static String MonitoringPreferencePage_deadlock_threshold_label;
-	public static String MonitoringPreferencePage_deadlock_threshold_too_low_error;
-	public static String MonitoringPreferencePage_enable_monitoring_label;
-	public static String MonitoringPreferencePage_filter_label;
-	public static String MonitoringPreferencePage_log_freeze_events_label;
-	public static String MonitoringPreferencePage_warning_threshold_label;
-	public static String MonitoringPreferencePage_error_threshold_label;
-	public static String MonitoringPreferencePage_error_threshold_too_low_error;
-	public static String MonitoringPreferencePage_max_stack_samples_label;
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
index 469db175c1..0000000000
--- a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/Messages.properties
+++ /dev/null
@@ -1,29 +0,0 @@
-###############################################################################
-# Copyright (c) 2014 Google, Inc and others.
-# All rights reserved. This program and the accompanying materials
-# are made available under the terms of the Eclipse Public License v1.0
-# which accompanies this distribution, and is available at
-# http://www.eclipse.org/legal/epl-v10.html
-#
-# Contributors:
-#	  Marcus Eng (Google) - initial API and implementation
-#	  Sergey Prigogin (Google)
-###############################################################################
-
-FilterInputDialog_filter_input_header=New Stack Trace Filter
-FilterInputDialog_filter_input_label=Stack frame to ignore:
-FilterInputDialog_filter_input_message=Enter the fully qualified method name of a stack frame to filter out. UI freezes containing this method in a stack trace of the UI thread are ignored.
-FilterInputDialog_filter_input_title=Add Filter
-FilterInputDialog_invalid_method_name=Invalid method name
-ListFieldEditor_add_filter_button_label=Add &Filter...
-MonitoringPreferenceListener_preference_error_header=Invalid Preferences
-MonitoringPreferenceListener_preference_error=The specified preferences could not be updated. See error log for details.
-MonitoringPreferencePage_deadlock_threshold_label=Deadl&ock threshold (ms):
-MonitoringPreferencePage_deadlock_threshold_too_low_error=The deadlock threshold must be higher than the error threshold.
-MonitoringPreferencePage_enable_monitoring_label=De&tect periods of unresponsive UI
-MonitoringPreferencePage_filter_label=&Stack frames to filter out:
-MonitoringPreferencePage_log_freeze_events_label=&Log UI freezes to Eclipse error log
-MonitoringPreferencePage_warning_threshold_label=&Warning threshold (ms):
-MonitoringPreferencePage_error_threshold_label=&Error threshold (ms):
-MonitoringPreferencePage_error_threshold_too_low_error=The error threshold cannot be lower than the warning threshold.
-MonitoringPreferencePage_max_stack_samples_label=&Maximum stack samples to log:
diff --git a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferenceInitializer.java b/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferenceInitializer.java
deleted file mode 100644
index d608a43fd8..0000000000
--- a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferenceInitializer.java
+++ /dev/null
@@ -1,39 +0,0 @@
-/*******************************************************************************
- * Copyright (C) 2014, Google Inc and others.
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
-	/** Force a logged event for a possible deadlock when an event hangs for longer than this */
-	private static final int DEFAULT_FORCE_DEADLOCK_LOG_TIME_MILLIS = 10 * 60 * 1000; // 10 minutes
-
-	@Override
-	public void initializeDefaultPreferences() {
-		IPreferenceStore store = MonitoringPlugin.getDefault().getPreferenceStore();
-
-		store.setDefault(PreferenceConstants.MONITORING_ENABLED, false);
-		store.setDefault(PreferenceConstants.LONG_EVENT_WARNING_THRESHOLD_MILLIS, 500); // 0.5 sec
-		store.setDefault(PreferenceConstants.LONG_EVENT_ERROR_THRESHOLD_MILLIS, 2000); // 2 sec
-		store.setDefault(PreferenceConstants.MAX_STACK_SAMPLES, 3);
-		store.setDefault(PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS,
-				DEFAULT_FORCE_DEADLOCK_LOG_TIME_MILLIS);
-		store.setDefault(PreferenceConstants.LOG_TO_ERROR_LOG, true);
-		store.setDefault(PreferenceConstants.FILTER_TRACES, "); //$NON-NLS-1$
-	}
-}
diff --git a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferenceListener.java b/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferenceListener.java
deleted file mode 100644
index 6a294ac906..0000000000
--- a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferenceListener.java
+++ /dev/null
@@ -1,98 +0,0 @@
-/*******************************************************************************
- * Copyright (C) 2014, Google Inc and others.
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
-import org.eclipse.jface.dialogs.MessageDialog;
-import org.eclipse.jface.preference.IPreferenceStore;
-import org.eclipse.jface.util.IPropertyChangeListener;
-import org.eclipse.jface.util.PropertyChangeEvent;
-import org.eclipse.swt.widgets.Display;
-import org.eclipse.ui.internal.monitoring.EventLoopMonitorThread;
-import org.eclipse.ui.internal.monitoring.MonitoringPlugin;
-import org.eclipse.ui.internal.monitoring.MonitoringStartup;
-import org.eclipse.ui.monitoring.PreferenceConstants;
-
-/**
- * Listens to preference changes and restarts the monitoring thread when necessary.
- */
-public class MonitoringPreferenceListener implements IPropertyChangeListener {
-	private EventLoopMonitorThread monitoringThread;
-	/**
-	 * A flag to handle the resetting of the {@link EventLoopMonitorThread}. The method
-	 * {@link #refreshMonitoringThread()} can be called multiple times if multiple preferences are
-	 * changed via the preference page. {@code monitorThreadRestartInProgress} is set on the first
-	 * call to {@link #refreshMonitoringThread()}. Subsequent calls to restartMonitorThread do not
-	 * schedule more resets while the flag is enabled. Once the scheduled asyncExec event executes,
-	 * the flag is reset.
-	 */
-	private boolean monitorThreadRestartInProgress;
-
-	public MonitoringPreferenceListener(EventLoopMonitorThread thread) {
-		monitoringThread = thread;
-	}
-
-	@Override
-	public void propertyChange(PropertyChangeEvent event) {
-		String property = event.getProperty();
-		if (!property.equals(PreferenceConstants.MONITORING_ENABLED)
-				&& !property.equals(PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS)
-				&& !property.equals(PreferenceConstants.LONG_EVENT_ERROR_THRESHOLD_MILLIS)
-				&& !property.equals(PreferenceConstants.LONG_EVENT_WARNING_THRESHOLD_MILLIS)
-				&& !property.equals(PreferenceConstants.LOG_TO_ERROR_LOG)
-				&& !property.equals(PreferenceConstants.MAX_STACK_SAMPLES)
-				&& !property.equals(PreferenceConstants.FILTER_TRACES)) {
-			return;
-		}
-
-		synchronized (this) {
-			if (monitorThreadRestartInProgress) {
-				return;
-			}
-
-			monitorThreadRestartInProgress = true;
-
-			final Display display = MonitoringPlugin.getDefault().getWorkbench().getDisplay();
-			// Schedule the event to restart the thread after all preferences have had enough time
-			// to propagate.
-			display.asyncExec(new Runnable() {
-				@Override
-				public void run() {
-					refreshMonitoringThread();
-				}
-			});
-		}
-	}
-
-	private synchronized void refreshMonitoringThread() {
-		if (monitoringThread != null) {
-			monitoringThread.shutdown();
-			monitoringThread = null;
-		}
-		monitorThreadRestartInProgress = false;
-
-		MonitoringPlugin plugin = MonitoringPlugin.getDefault();
-		IPreferenceStore preferences = plugin.getPreferenceStore();
-		if (preferences.getBoolean(PreferenceConstants.MONITORING_ENABLED)) {
-			EventLoopMonitorThread thread = MonitoringStartup.createAndStartMonitorThread();
-			// If thread is null, the newly-defined preferences are invalid.
-			if (thread == null) {
-				MessageDialog.openError(
-						plugin.getWorkbench().getActiveWorkbenchWindow().getShell(),
-						Messages.MonitoringPreferenceListener_preference_error_header,
-						Messages.MonitoringPreferenceListener_preference_error);
-				return;
-			}
-
-			monitoringThread = thread;
-		}
-	}
-}
diff --git a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferencePage.java b/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferencePage.java
deleted file mode 100644
index 134ef8852d..0000000000
--- a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/internal/monitoring/preferences/MonitoringPreferencePage.java
+++ /dev/null
@@ -1,217 +0,0 @@
-/*******************************************************************************
- * Copyright (C) 2014, Google Inc and others.
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
-import java.util.HashMap;
-import java.util.Map;
-
-import org.eclipse.jface.layout.PixelConverter;
-import org.eclipse.jface.preference.BooleanFieldEditor;
-import org.eclipse.jface.preference.FieldEditor;
-import org.eclipse.jface.preference.FieldEditorPreferencePage;
-import org.eclipse.jface.preference.IPreferenceStore;
-import org.eclipse.jface.preference.IntegerFieldEditor;
-import org.eclipse.jface.util.PropertyChangeEvent;
-import org.eclipse.swt.SWT;
-import org.eclipse.swt.layout.GridData;
-import org.eclipse.swt.layout.GridLayout;
-import org.eclipse.swt.widgets.Composite;
-import org.eclipse.ui.IWorkbench;
-import org.eclipse.ui.IWorkbenchPreferencePage;
-import org.eclipse.ui.internal.monitoring.MonitoringPlugin;
-import org.eclipse.ui.monitoring.PreferenceConstants;
-
-/**
- * Preference page that allows user to toggle plug in settings from Eclipse preferences.
- */
-public class MonitoringPreferencePage extends FieldEditorPreferencePage
-		implements IWorkbenchPreferencePage {
-	private static final int HOUR_IN_MS = 3600000;
-	private static final IPreferenceStore preferences =
-			MonitoringPlugin.getDefault().getPreferenceStore();
-	private BooleanFieldEditor monitoringEnabled;
-	private IntegerEditor longEventWarningThreshold;
-	private IntegerEditor longEventErrorThreshold;
-	private IntegerEditor deadlockThreshold;
-	private Map<FieldEditor, Composite> editors;
-
-	private class IntegerEditor extends IntegerFieldEditor {
-		public IntegerEditor(String name, String labelText, Composite parent, int min, int max) {
-	    	super(name, labelText, parent);
-	    	setValidRange(min, max);
-		}
-
-		@Override
-		protected void valueChanged() {
-			super.valueChanged();
-			if (longEventWarningThreshold.isValid() && longEventErrorThreshold.checkValue()) {
-				deadlockThreshold.checkValue();
-			}
-		}
-
-		@Override
-		protected boolean checkState() {
-			if (!super.checkState()) {
-				return false;
-			}
-
-			String preferenceName = getPreferenceName();
-			if (preferenceName.equals(PreferenceConstants.LONG_EVENT_ERROR_THRESHOLD_MILLIS)) {
-				if (longEventWarningThreshold.isValid() &&
-						getIntValue() < longEventWarningThreshold.getIntValue()) {
-					showMessage(Messages.MonitoringPreferencePage_error_threshold_too_low_error);
-					return false;
-				}
-			} else if (preferenceName.equals(PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS)) {
-				if (longEventWarningThreshold.isValid() &&
-						getIntValue() <= longEventErrorThreshold.getIntValue()) {
-					showMessage(Messages.MonitoringPreferencePage_deadlock_threshold_too_low_error);
-					return false;
-				}
-			}
-			return true;
-		}
-
-		private boolean checkValue() {
-	        boolean oldState = isValid();
-	        refreshValidState();
-
-	        boolean isValid = isValid();
-	        if (isValid != oldState) {
-				fireStateChanged(IS_VALID, oldState, isValid);
-			}
-	        return isValid;
-	    }
-	}
-
-	public MonitoringPreferencePage() {
-		super(GRID);
-		editors = new HashMap<FieldEditor, Composite>();
-	}
-
-	@Override
-	public void createFieldEditors() {
-		Composite parent = getFieldEditorParent();
-    	PixelConverter pixelConverter = new PixelConverter(parent);
-		GridLayout layout = new GridLayout(1, false);
-		layout.marginWidth = 0;
-		parent.setLayout(layout);
-
-		Composite container = new Composite(parent, SWT.NONE);
-		layout = new GridLayout(1, false);
-		layout.marginWidth = 0;
-		layout.marginHeight = 0;
-		layout.verticalSpacing = pixelConverter.convertHeightInCharsToPixels(1);
-		container.setLayout(layout);
-		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
-
-		Composite topGroup = new Composite(container, SWT.NONE);
-		layout = new GridLayout(2, false);
-		layout.marginWidth = 0;
-		layout.marginHeight = 0;
-		topGroup.setLayout(layout);
-		topGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
-
-		monitoringEnabled = createBooleanEditor(PreferenceConstants.MONITORING_ENABLED,
-				Messages.MonitoringPreferencePage_enable_monitoring_label, topGroup);
-
-		longEventWarningThreshold = createIntegerEditor(
-				PreferenceConstants.LONG_EVENT_WARNING_THRESHOLD_MILLIS,
-				Messages.MonitoringPreferencePage_warning_threshold_label, topGroup,
-				3, HOUR_IN_MS);
-		longEventErrorThreshold = createIntegerEditor(
-				PreferenceConstants.LONG_EVENT_ERROR_THRESHOLD_MILLIS,
-				Messages.MonitoringPreferencePage_error_threshold_label, topGroup,
-				3, HOUR_IN_MS);
-		deadlockThreshold = createIntegerEditor(
-				PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS,
-				Messages.MonitoringPreferencePage_deadlock_threshold_label, topGroup,
-				1000, 24 * HOUR_IN_MS);
-		createIntegerEditor(
-				PreferenceConstants.MAX_STACK_SAMPLES,
-				Messages.MonitoringPreferencePage_max_stack_samples_label, topGroup, 0, 100);
-
-		topGroup.setLayout(layout);
-
-		createBooleanEditor(PreferenceConstants.LOG_TO_ERROR_LOG,
-				Messages.MonitoringPreferencePage_log_freeze_events_label, topGroup);
-		topGroup.setLayout(layout);
-
-		final Composite bottomGroup = new Composite(container, SWT.NONE);
-		layout = new GridLayout(2, false);
-		layout.marginWidth = 0;
-		layout.marginHeight = 0;
-		bottomGroup.setLayout(layout);
-		bottomGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
-
-		addField(new FilterListEditor(PreferenceConstants.FILTER_TRACES,
-				Messages.MonitoringPreferencePage_filter_label, bottomGroup), bottomGroup);
-	}
-
-	@Override
-	public void init(IWorkbench workbench) {
-		setPreferenceStore(preferences);
-	}
-
-	@Override
-	public void propertyChange(PropertyChangeEvent event) {
-        if (event.getProperty().equals(FieldEditor.VALUE)) {
-    		Object source = event.getSource();
-    		if (source instanceof FieldEditor) {
-    			String preferenceName = ((FieldEditor) source).getPreferenceName();
-				if (preferenceName.equals(PreferenceConstants.MONITORING_ENABLED)) {
-    				boolean enabled = Boolean.TRUE.equals(event.getNewValue());
-	    			enableDependentFields(enabled);
-    			}
-    		}
-        }
-		super.propertyChange(event);
-	}
-
-	@Override
-	protected void performDefaults() {
-		super.performDefaults();
-		enableDependentFields(monitoringEnabled.getBooleanValue());
-	}
-
-	private void enableDependentFields(boolean enable) {
-		for (Map.Entry<FieldEditor, Composite> entry : editors.entrySet()) {
-			FieldEditor editor = entry.getKey();
-			if (!editor.getPreferenceName().equals(PreferenceConstants.MONITORING_ENABLED)) {
-				editor.setEnabled(enable, entry.getValue());
-			}
-		}
-	}
-
-	private BooleanFieldEditor createBooleanEditor(String name, String labelText,
-			Composite parent) {
-		BooleanFieldEditor field = new BooleanFieldEditor(name, labelText, parent);
-		return addField(field, parent);
-	}
-
-	private IntegerEditor createIntegerEditor(String name, String labelText, Composite parent,
-			int min, int max) {
-		IntegerEditor field = new IntegerEditor(name, labelText, parent, min, max);
-		return addField(field, parent);
-	}
-
-	private <T extends FieldEditor> T addField(T editor, Composite parent) {
-		super.addField(editor);
-		editor.fillIntoGrid(parent, 2);
-		editors.put(editor, parent);
-		if (!editor.getPreferenceName().equals(PreferenceConstants.MONITORING_ENABLED)) {
-			boolean enabled = preferences.getBoolean(PreferenceConstants.MONITORING_ENABLED);
-			editor.setEnabled(enabled, parent);
-		}
-		return editor;
-	}
-}
diff --git a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/monitoring/IUiFreezeEventLogger.java b/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/monitoring/IUiFreezeEventLogger.java
deleted file mode 100644
index e7020cf894..0000000000
--- a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/monitoring/IUiFreezeEventLogger.java
+++ /dev/null
@@ -1,27 +0,0 @@
-/*******************************************************************************
- * Copyright (C) 2014, Google Inc and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     Marcus Eng (Google) - initial API and implementation
- *******************************************************************************/
-package org.eclipse.ui.monitoring;
-
-import org.eclipse.ui.internal.monitoring.EventLoopMonitorThread;
-
-/**
- * All classes logging {@link UiFreezeEvent}s have to implement this interface.
- *
- * @since 1.0
- */
-public interface IUiFreezeEventLogger {
-	/**
-	 * Invoked from the {@link EventLoopMonitorThread} whenever a {@link UiFreezeEvent} is ready to
-	 * be logged. Implementations of this function must end quickly or else it will impact system
-	 * performance. All time-consuming tasks should be executed asynchronously.
-	 */
-	void log(UiFreezeEvent event);
-}
diff --git a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/monitoring/PreferenceConstants.java b/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/monitoring/PreferenceConstants.java
deleted file mode 100644
index 38dfce22d5..0000000000
--- a/bundles/org.eclipse.ui.monitoring/src/org/eclipse/ui/monitoring/PreferenceConstants.java
+++ /dev/null
@@ -1,53 +0,0 @@
-/*******************************************************************************
- * Copyright (C) 2014, Google Inc and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *	   Marcus Eng (Google) - initial API and implementation
- *	   Sergey Prigogin (Google)
- *******************************************************************************/
-package org.eclipse.ui.monitoring;
-
-/**
- * Definitions of the preference constants.
- *
- * @since 1.0
- */
-public class PreferenceConstants {
-	public static final String PLUGIN_ID = org.eclipse.ui.monitoring"; //$NON-NLS-1$
	/**
	 * If true, enables the monitoring thread which logs events which take long time to process.
	 */
	/**
	 * Events that took longer than the specified duration in milliseconds are logged as warnings.
	 */
	/**
	 * Events that took longer than the specified duration in milliseconds are logged as errors.
	 */
	/**
	 * Events that took longer than the specified duration are reported as deadlocks without waiting
	 * for the event to finish.
	 */
	/**
	 * Maximum number of stack trace samples to write out to the log.
	 */
	/**
	 * If true, log freeze events to the Eclipse error log.
	 */
	/**
	 * Comma separated fully qualified method names of stack frames to filter out.
	 * Long events containing this method in a stack trace of the UI thread are ignored.
	 */

	private PreferenceConstants() {}
}
