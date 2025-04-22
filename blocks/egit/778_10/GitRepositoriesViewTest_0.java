package org.eclipse.egit.ui.test;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.egit.ui.Activator;
import org.eclipse.osgi.service.localization.BundleLocalization;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class TestUtil {

	private static final char AMPERSAND = '&';

	private static final TestUtil INSTANCE = new TestUtil();

	public static TestUtil getInstance() {
		return INSTANCE;
	}

	private ResourceBundle myBundle;

	public synchronized String getPluginLocalizedValue(String key)
			throws MissingResourceException {
		return getPluginLocalizedValue(key, false);
	}

	public synchronized String getPluginLocalizedValue(String key,
			boolean keepAmpersands) throws MissingResourceException {
		if (myBundle == null) {
			ServiceTracker localizationTracker;

			BundleContext context = Activator.getDefault().getBundle()
					.getBundleContext();

			localizationTracker = new ServiceTracker(context,
					BundleLocalization.class.getName(), null);
			localizationTracker.open();

			BundleLocalization location = (BundleLocalization) localizationTracker
					.getService();
			if (location != null)
				myBundle = location.getLocalization(Activator.getDefault()
						.getBundle(), Locale.getDefault().toString());
		}

		String raw = myBundle.getString(key);

		if (keepAmpersands || raw.indexOf(AMPERSAND) < 0)
			return raw;

		StringBuilder sb = new StringBuilder(raw.length());
		for (int i = 0; i < raw.length(); i++) {
			char c = raw.charAt(i);
			if (c != AMPERSAND) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
