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
import java.util.Optional;
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
import org.osgi.framework.FrameworkUtil;

public class DialogSettingsService {

	public static final DialogSettingsService instance = new DialogSettingsService();

	private static final String WORKBENCH = "Workbench"; //$NON-NLS-1$

	private Map<String, IDialogSettings> fTrackedBundles = new HashMap<>();

	private final String FN_DIALOG_SETTINGS = "dialog_settings.xml"; //$NON-NLS-1$

	private final String KEY_DEFAULT_DIALOG_SETTINGS_ROOTURL = "default_dialog_settings_rootUrl"; //$NON-NLS-1$

	private DialogSettingsService() {
		addBundleListener();
	}

	private void addBundleListener() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		bundle.getBundleContext().addBundleListener(event -> {
			handleBundleStopped(event);
		});
	}

	private void handleBundleStopped(BundleEvent event) {
		if (event.getType() == BundleEvent.STOPPED || event.getType() == BundleEvent.STOPPING) {
			synchronized (fTrackedBundles) {
				if (fTrackedBundles.containsKey(event.getBundle().getSymbolicName())) {
					saveDialogSettings(event.getBundle());
					fTrackedBundles.remove(event.getBundle().getSymbolicName());
				}
			}
		}
	}

	public IDialogSettings loadDialogSettings(Bundle bundle) {
		IDialogSettings dialogSettings = loadFromCache(bundle).orElseGet(() -> loadDialogSettingsFromWorkspace(bundle)
				.orElseGet(() -> loadDefaultDialogSettingsFromProduct(bundle).orElseGet(
						() -> loadDefaultDialogSettingsFromBundle(bundle).orElseGet(() -> createEmptySettings()))));
		trackBundle(bundle, dialogSettings);
		return dialogSettings;
	}

	private synchronized void trackBundle(Bundle bundle, final IDialogSettings settings) {
		if (!fTrackedBundles.containsKey(bundle.getSymbolicName())) {
			fTrackedBundles.put(bundle.getSymbolicName(), settings);
		}
	}

	private Optional<IDialogSettings> loadDefaultDialogSettingsFromProduct(Bundle bundle) {
		IPreferencesService preferencesService = Platform.getPreferencesService();
		String rootUrl = preferencesService.getRootNode().get(KEY_DEFAULT_DIALOG_SETTINGS_ROOTURL, ""); //$NON-NLS-1$

		if (rootUrl == null || rootUrl.isEmpty()) {
			return Optional.empty();
		}
		String bundlePart = bundle.getSymbolicName() + "/" + FN_DIALOG_SETTINGS; //$NON-NLS-1$
		String fullUrl = rootUrl.endsWith("/") ? rootUrl + bundlePart : rootUrl + "/" + bundlePart; //$NON-NLS-1$//$NON-NLS-2$
		URL url;
		try {
			url = new URL(fullUrl);
		} catch (MalformedURLException e) {
			Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to load dialog settings from: " + fullUrl, e)); //$NON-NLS-1$
			return Optional.empty();
		}

		try {
			url = FileLocator.resolve(url);
		} catch (IOException e) {
			Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to load dialog settings from: " + fullUrl, e)); //$NON-NLS-1$
			return Optional.empty();
		}

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
			IDialogSettings dialogSettings = createEmptySettings(); // $NON-NLS-1$
			dialogSettings.load(reader);
			return Optional.of(dialogSettings);
		} catch (IOException e) {
			Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to load dialog settings from: " + url, e)); //$NON-NLS-1$
		}
		return Optional.empty();
	}


	private Optional<IDialogSettings> loadDialogSettingsFromWorkspace(Bundle bundle) {
		IPath dataLocation = getStateLocationOrNull(bundle);
		if (dataLocation == null) {
			return Optional.empty();
		}
		String readWritePath = dataLocation.append(FN_DIALOG_SETTINGS).toOSString();
		File settingsFile = new File(readWritePath);
		if (settingsFile.exists()) {
			try {
				IDialogSettings dialogSettings = createEmptySettings();
				dialogSettings.load(readWritePath);
				return Optional.of(dialogSettings);
			} catch (IOException e) {
				Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
						"Failed to load dialog settings from: " + settingsFile, e)); //$NON-NLS-1$
			}
		}
		return Optional.empty();
	}

	private Optional<IDialogSettings> loadDefaultDialogSettingsFromBundle(Bundle bundle) {
		URL dsURL = FileLocator.find(bundle, new Path(FN_DIALOG_SETTINGS));
		IDialogSettings dialogSettings = null;
		if (dsURL == null) {
			return Optional.empty();
		}
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(dsURL.openStream(), StandardCharsets.UTF_8))) {
			dialogSettings = createEmptySettings();
			dialogSettings.load(reader);
			return Optional.of(dialogSettings);
		} catch (IOException e) {
			Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to load dialog settings from: " + dsURL, e)); //$NON-NLS-1$
		}
		return Optional.empty();
	}

	private static IDialogSettings createEmptySettings() {
		return new DialogSettings(WORKBENCH);
	}

	private Optional<IDialogSettings> loadFromCache(Bundle bundle) {
		return Optional.ofNullable(fTrackedBundles.get(bundle.getSymbolicName()));
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

	protected void saveDialogSettings(Bundle bundle) {
		IDialogSettings dialogSettings = fTrackedBundles.get(bundle.getSymbolicName());
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
}
