import static org.eclipse.ui.monitoring.PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS;
import static org.eclipse.ui.monitoring.PreferenceConstants.DUMP_ALL_THREADS;
import static org.eclipse.ui.monitoring.PreferenceConstants.FILTER_TRACES;
import static org.eclipse.ui.monitoring.PreferenceConstants.INITIAL_SAMPLE_DELAY_MILLIS;
import static org.eclipse.ui.monitoring.PreferenceConstants.LOG_TO_ERROR_LOG;
import static org.eclipse.ui.monitoring.PreferenceConstants.LONG_EVENT_THRESHOLD_MILLIS;
import static org.eclipse.ui.monitoring.PreferenceConstants.MAX_STACK_SAMPLES;
import static org.eclipse.ui.monitoring.PreferenceConstants.MONITORING_ENABLED;
import static org.eclipse.ui.monitoring.PreferenceConstants.PLUGIN_ID;
import static org.eclipse.ui.monitoring.PreferenceConstants.SAMPLE_INTERVAL_MILLIS;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
