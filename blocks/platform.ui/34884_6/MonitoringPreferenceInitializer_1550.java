package org.eclipse.ui.internal.monitoring.preferences;

import org.eclipse.osgi.util.NLS;

public final class Messages extends NLS {
	public static String FilterInputDialog_filter_input_header;
	public static String FilterInputDialog_filter_input_message;
	public static String FilterInputDialog_filter_input_label;
	public static String FilterInputDialog_filter_input_title;
	public static String FilterInputDialog_invalid_method_name;
	public static String ListFieldEditor_add_filter_button_label;
	public static String MonitoringPreferenceListener_preference_error_header;
	public static String MonitoringPreferenceListener_preference_error;
	public static String MonitoringPreferencePage_deadlock_threshold_label;
	public static String MonitoringPreferencePage_deadlock_threshold_too_low_error;
	public static String MonitoringPreferencePage_enable_monitoring_label;
	public static String MonitoringPreferencePage_filter_label;
	public static String MonitoringPreferencePage_log_freeze_events_label;
	public static String MonitoringPreferencePage_warning_threshold_label;
	public static String MonitoringPreferencePage_error_threshold_label;
	public static String MonitoringPreferencePage_error_threshold_too_low_error;
	public static String MonitoringPreferencePage_max_stack_samples_label;

	private Messages() {
	}

	static {
		NLS.initializeMessages(Messages.class.getName(), Messages.class);
	}
}
