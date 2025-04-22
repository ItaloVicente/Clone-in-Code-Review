package org.eclipse.e4.ui.internal.workbench.swt;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.WeakHashMap;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.workbench.UIEvents.UILifeCycle;
import org.eclipse.jface.dialogs.IDialogSettingsProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

public final class DialogSettingsProviderService {

	public static final DialogSettingsProviderService instance = new DialogSettingsProviderService();

	private static Map<Bundle, DialogSettingsProvider> fTrackedBundles = Collections
			.synchronizedMap(new WeakHashMap<>());

	static {
		BundleContext ctx = FrameworkUtil.getBundle(DialogSettingsProvider.class).getBundleContext();
		Dictionary<String, String[]> topics = new Hashtable<>();
		topics.put(EventConstants.EVENT_TOPIC, new String[] { UILifeCycle.APP_SHUTDOWN_STARTED });
		ctx.registerService(EventHandler.class, new EventHandler() {

			private boolean fSaved;

			@Override
			public void handleEvent(Event event) {
				if (event.getTopic().equals(UILifeCycle.APP_SHUTDOWN_STARTED)) {
					if (!fSaved) {
						if (Platform.inDebugMode()) {
							Platform.getLog(ctx.getBundle()).info("Saving dialog settings"); //$NON-NLS-1$
						}
						fTrackedBundles.forEach((bundle, service) -> service.saveDialogSettings());
						fSaved = true;
					}
				}

			}
		}, topics);
	}

	public synchronized IDialogSettingsProvider getProvider(Bundle bundle) {
		if (fTrackedBundles.containsKey(bundle)) {
			return fTrackedBundles.get(bundle);
		}
		DialogSettingsProvider dialogSettingsProvider = new DialogSettingsProvider(bundle);
		fTrackedBundles.put(bundle, dialogSettingsProvider);
		return dialogSettingsProvider;
	}

}
