
package org.eclipse.ui.internal;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.NodeChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.internal.InternalPolicy;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.util.ILogger;
import org.eclipse.jface.util.ISafeRunnableRunner;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.util.StatusHandler;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;

final class JFaceUtil {

	private JFaceUtil() {
	}

	public static void initializeJFace() {
		SafeRunnable.setRunner(new ISafeRunnableRunner() {
			@Override
			public void run(ISafeRunnable code) {
				SafeRunner.run(code);
			}
		});

		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
				if (status.getSeverity() == IStatus.WARNING
						|| status.getSeverity() == IStatus.ERROR) {
					StatusManager.getManager().handle(status);
				} else {
					WorkbenchPlugin.log(status);
				}
			}
		});
		
		Policy.setStatusHandler(new StatusHandler() {
			@Override
			public void show(IStatus status, String title) {
				StatusAdapter statusAdapter = new StatusAdapter(status);
				statusAdapter.setProperty(StatusAdapter.TITLE_PROPERTY, title);
				StatusManager.getManager().handle(statusAdapter, StatusManager.SHOW);
			}
		});

		if ("true".equalsIgnoreCase(Platform.getDebugOption(Policy.JFACE + "/debug"))) { //$NON-NLS-1$ //$NON-NLS-2$
			Policy.DEBUG_DIALOG_NO_PARENT = "true".equalsIgnoreCase(Platform.getDebugOption(Policy.JFACE + "/debug/dialog/noparent")); //$NON-NLS-1$ //$NON-NLS-2$
			Policy.TRACE_ACTIONS = "true".equalsIgnoreCase(Platform.getDebugOption(Policy.JFACE + "/trace/actions")); //$NON-NLS-1$ //$NON-NLS-2$
			Policy.TRACE_TOOLBAR = "true".equalsIgnoreCase(Platform.getDebugOption(Policy.JFACE + "/trace/toolbarDisposal")); //$NON-NLS-1$ //$NON-NLS-2$
			InternalPolicy.DEBUG_LOG_REENTRANT_VIEWER_CALLS = "true".equalsIgnoreCase(Platform.getDebugOption(Policy.JFACE + "/debug/viewers/reentrantViewerCalls")); //$NON-NLS-1$ //$NON-NLS-2$
			InternalPolicy.DEBUG_LOG_EQUAL_VIEWER_ELEMENTS = "true".equalsIgnoreCase(Platform.getDebugOption(Policy.JFACE + "/debug/viewers/equalElements")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	public static void initializeJFacePreferences() {
		IEclipsePreferences rootNode = (IEclipsePreferences) Platform.getPreferencesService().getRootNode().node(InstanceScope.SCOPE);
		final String workbenchName = WorkbenchPlugin.getDefault().getBundle().getSymbolicName();
		
		rootNode.addNodeChangeListener(new IEclipsePreferences.INodeChangeListener() {
			@Override
			public void added(NodeChangeEvent event) {
				if (!event.getChild().name().equals(workbenchName)) {
					return;
				}
				((IEclipsePreferences) event.getChild()).addPreferenceChangeListener(PlatformUIPreferenceListener.getSingleton());

			}
			@Override
			public void removed(NodeChangeEvent event) {

			}
		});
		
		JFacePreferences.setPreferenceStore(WorkbenchPlugin.getDefault().getPreferenceStore());
	}
}
