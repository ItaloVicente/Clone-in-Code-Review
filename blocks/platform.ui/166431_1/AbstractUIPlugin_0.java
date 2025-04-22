package org.eclipse.ui.dialogs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class DialogSettingsUtil {

	private static final String FN_DIALOG_SETTINGS = "dialog_settings.xml"; //$NON-NLS-1$

	private static final String KEY_DEFAULT_DIALOG_SETTINGS_ROOTURL = "default_dialog_settings_rootUrl"; //$NON-NLS-1$

	public static IDialogSettings loadDialogSettings(Class<?> bundleClass) {
		DialogSettings dialogSettings = createEmptySettings("Workbench"); //$NON-NLS-1$
		boolean loaded = loadDialogSettingsFromWorkspace(dialogSettings, bundleClass);
		if (!loaded) {
			loaded = loadDefaultDialogSettingsFromProduct(dialogSettings, bundleClass);
		}
		if (!loaded) {
			loadDefaultDialogSettingsFromBundle(dialogSettings, bundleClass);
		}

		return dialogSettings;
	}

	private static boolean loadDefaultDialogSettingsFromProduct(IDialogSettings dialogSettings, Class<?> bundleClass) {
		String rootUrl = PlatformUI.getPreferenceStore().getString(KEY_DEFAULT_DIALOG_SETTINGS_ROOTURL);
		if (rootUrl == null || rootUrl.isEmpty()) {
			return false;
		}
		String bundlePart = getBundle(bundleClass).getSymbolicName() + "/" + FN_DIALOG_SETTINGS; //$NON-NLS-1$
		String fullUrl = rootUrl.endsWith("/") ? rootUrl + bundlePart : rootUrl + "/" + bundlePart; //$NON-NLS-1$//$NON-NLS-2$
		URL url;
		try {
			url = new URL(fullUrl);
		} catch (MalformedURLException e) {
			getLog(bundleClass).log(new Status(IStatus.ERROR, getBundle(bundleClass).getSymbolicName(),
					"Failed to load dialog settings from: " + fullUrl, e)); //$NON-NLS-1$
			return false;
		}

		try {
			url = FileLocator.resolve(url);
		} catch (IOException e) {
			if (WorkbenchPlugin.DEBUG) {
				getLog(bundleClass).log(new Status(IStatus.ERROR, getBundle(bundleClass).getSymbolicName(),
						"Failed to load dialog settings from: " + fullUrl, e)); //$NON-NLS-1$
			}
			return false;
		}

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
			dialogSettings.load(reader);
			return true;
		} catch (IOException e) {
			dialogSettings = createEmptySettings("Workbench"); //$NON-NLS-1$
			getLog(bundleClass).log(new Status(IStatus.ERROR, getBundle(bundleClass).getSymbolicName(),
					"Failed to load dialog settings from: " + url, e)); //$NON-NLS-1$
		}
		return false;
	}

	private static boolean loadDialogSettingsFromWorkspace(IDialogSettings dialogSettings, Class<?> bundleClass) {
		IPath dataLocation = getStateLocationOrNull(bundleClass);
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
				getLog(bundleClass).log(new Status(IStatus.ERROR, getBundle(bundleClass).getSymbolicName(),
						"Failed to load dialog settings from: " + settingsFile, e)); //$NON-NLS-1$
			}
			return true;
		}
		return false;
	}

	private static void loadDefaultDialogSettingsFromBundle(IDialogSettings dialogSettings, Class<?> bundleClass) {
		Bundle bundle = getBundle(bundleClass);
		URL dsURL = BundleUtility.find(bundle, FN_DIALOG_SETTINGS);
		if (dsURL == null) {
			return;
		}
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(dsURL.openStream(), StandardCharsets.UTF_8))) {
			dialogSettings.load(reader);
		} catch (IOException e) {
			getLog(bundleClass).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
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

	private final static ILog getLog(Class<?> bundleClass) {
		return InternalPlatform.getDefault().getLog(getBundle(bundleClass));
	}

	private final static IPath getStateLocation(Class<?> bundleClass) throws IllegalStateException {
		return InternalPlatform.getDefault().getStateLocation(getBundle(bundleClass), true);
	}

	private static IPath getStateLocationOrNull(Class<?> bundleClass) {
		try {
			return getStateLocation(bundleClass);
		} catch (IllegalStateException e) {
			return null;
		}
	}

	public static void saveDialogSettings(IDialogSettings dialogSettings, Class<?> bundleClass) {
		if (dialogSettings == null) {
			return;
		}

		try {
			IPath path = getStateLocationOrNull(bundleClass);
			if (path == null) {
				return;
			}
			String readWritePath = path.append(FN_DIALOG_SETTINGS).toOSString();
			dialogSettings.save(readWritePath);
		} catch (IOException | IllegalStateException e) {
			Bundle bundle = getBundle(bundleClass);
			getLog(bundleClass).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to save dialog settings for bundle: " + bundle.getBundleId(), e)); //$NON-NLS-1$
		}
	}

}
