package org.eclipse.ui.dialogs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.FrameworkUtil;

public class DialogSettingsService {

	private static Map<Bundle, IDialogSettings> fTrackedBundles = new HashMap<>();

	private static final String FN_DIALOG_SETTINGS = "dialog_settings.xml"; //$NON-NLS-1$

	private static final String KEY_DEFAULT_DIALOG_SETTINGS_ROOTURL = "default_dialog_settings_rootUrl"; //$NON-NLS-1$

	public static IDialogSettings loadDialogSettings(Class<?> bundleClass) {
		return loadDialogSettings("Workbench", bundleClass, true); //$NON-NLS-1$
	}

	public static IDialogSettings loadDialogSettings(String section, Class<?> bundleClass, boolean track) {
		DialogSettings dialogSettings = createEmptySettings(section);
		boolean loaded = loadDialogSettingsFromWorkspace(dialogSettings, bundleClass);
		if (!loaded) {
			loaded = loadDefaultDialogSettingsFromProduct(dialogSettings, bundleClass);
		}
		if (!loaded) {
			loadDefaultDialogSettingsFromBundle(dialogSettings, bundleClass);
		}

		if (track) {
			trackBundle(getBundle(bundleClass), dialogSettings);
		}

		return dialogSettings;
	}

	private static synchronized void trackBundle(Bundle bundle, final IDialogSettings settings) {
		if (!fTrackedBundles.containsKey(bundle)) {
			fTrackedBundles.put(bundle, settings);
			bundle.getBundleContext().addBundleListener(new BundleListener() {

				@Override
				public void bundleChanged(BundleEvent event) {
					if (event.getType() == BundleEvent.STOPPING) {
						synchronized (fTrackedBundles) {
							if (fTrackedBundles.containsKey(event.getBundle())) {
								IDialogSettings settings = fTrackedBundles.get(event.getBundle());
								fTrackedBundles.remove(event.getBundle());
								DialogSettingsService.saveDialogSettings(settings, event.getBundle());
							}
						}
					}
				}
			});
		}
	}

	private static boolean loadDefaultDialogSettingsFromProduct(IDialogSettings dialogSettings, Class<?> bundleClass) {
		IPreferencesService preferencesService = Platform.getPreferencesService();
		String rootUrl = preferencesService.getRootNode().get(KEY_DEFAULT_DIALOG_SETTINGS_ROOTURL, ""); //$NON-NLS-1$

		if (rootUrl == null || rootUrl.isEmpty()) {
			return false;
		}
		String bundlePart = getBundle(bundleClass).getSymbolicName() + "/" + FN_DIALOG_SETTINGS; //$NON-NLS-1$
		String fullUrl = rootUrl.endsWith("/") ? rootUrl + bundlePart : rootUrl + "/" + bundlePart; //$NON-NLS-1$//$NON-NLS-2$
		URL url;
		try {
			url = new URL(fullUrl);
		} catch (MalformedURLException e) {
			Platform.getLog(bundleClass).log(new Status(IStatus.ERROR, getBundle(bundleClass).getSymbolicName(),
					"Failed to load dialog settings from: " + fullUrl, e)); //$NON-NLS-1$
			return false;
		}

		try {
			url = FileLocator.resolve(url);
		} catch (IOException e) {
			Platform.getLog(bundleClass).log(new Status(IStatus.ERROR, getBundle(bundleClass).getSymbolicName(),
					"Failed to load dialog settings from: " + fullUrl, e)); //$NON-NLS-1$
			return false;
		}

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
			dialogSettings.load(reader);
			return true;
		} catch (IOException e) {
			dialogSettings = createEmptySettings("Workbench"); //$NON-NLS-1$
			Platform.getLog(bundleClass).log(new Status(IStatus.ERROR, getBundle(bundleClass).getSymbolicName(),
					"Failed to load dialog settings from: " + url, e)); //$NON-NLS-1$
		}
		return false;
	}

	private static boolean loadDialogSettingsFromWorkspace(IDialogSettings dialogSettings, Class<?> bundleClass) {
		IPath dataLocation = getStateLocationOrNull(getBundle(bundleClass));
		if (dataLocation == null) {
			return false;
		}
		String readWritePath = dataLocation.append(FN_DIALOG_SETTINGS).toOSString();
		File settingsFile = new File(readWritePath);
		if (settingsFile.exists()) {
			try {
				dialogSettings.load(readWritePath);
			} catch (IOException e) {
				dialogSettings = createEmptySettings("Workbench"); //$NON-NLS-1$
				Platform.getLog(bundleClass).log(new Status(IStatus.ERROR, getBundle(bundleClass).getSymbolicName(),
						"Failed to load dialog settings from: " + settingsFile, e)); //$NON-NLS-1$
			}
			return true;
		}
		return false;
	}

	private static void loadDefaultDialogSettingsFromBundle(IDialogSettings dialogSettings, Class<?> bundleClass) {
		Bundle bundle = getBundle(bundleClass);
		URL dsURL = FileLocator.find(bundle, new Path(FN_DIALOG_SETTINGS));
		if (dsURL == null) {
			return;
		}
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(dsURL.openStream(), StandardCharsets.UTF_8))) {
			dialogSettings.load(reader);
		} catch (IOException e) {
			Platform.getLog(bundleClass).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to load dialog settings from: " + dsURL, e)); //$NON-NLS-1$
			dialogSettings = createEmptySettings("Workbench"); //$NON-NLS-1$
		}
	}

	private static Bundle getBundle(Class<?> bundleClass) {
		return FrameworkUtil.getBundle(bundleClass);
	}

	private static DialogSettings createEmptySettings(String sectionName) {
		return new DialogSettings(sectionName);
	}

	private static IPath getStateLocationOrNull(Bundle bundle) {
		try {
			return Platform.getStateLocation(bundle);
		} catch (IllegalStateException e) {
			Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to get state location for bundle: " + bundle, e)); //$NON-NLS-1$
			return null;
		}
	}

	private static void saveDialogSettings(IDialogSettings dialogSettings, Bundle bundle) {
		if (dialogSettings == null) {
			return;
		}
		try {
			IPath path = getStateLocationOrNull(bundle);
			if (path == null) {
				return;
			}
			String readWritePath = path.append(FN_DIALOG_SETTINGS).toOSString();
			dialogSettings.save(readWritePath);
		} catch (IOException | IllegalStateException e) {
			Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to save dialog settings for bundle: " + bundle.getBundleId(), e)); //$NON-NLS-1$
		}
	}

	public static void saveDialogSettings(IDialogSettings dialogSettings, Class<?> bundleClass) {
		saveDialogSettings(dialogSettings, getBundle(bundleClass));
	}

}
