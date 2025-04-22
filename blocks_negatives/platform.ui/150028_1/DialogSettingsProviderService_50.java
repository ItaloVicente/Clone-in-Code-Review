/*******************************************************************************
 * Copyright (c) 2000, 2020 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Remain BV       - Extract preferences from AbstratUIPlugin (549929)
 *
*******************************************************************************/
package org.eclipse.e4.ui.internal.workbench.swt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
import org.eclipse.jface.dialogs.IDialogSettingsProvider;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.Preferences;

/**
 * Loads, caches and stores dialog settings on a per bundle basis.
 *
 */
public final class DialogSettingsProvider implements IDialogSettingsProvider {


	/** instance scope */

	/** default scope */

	/** Platform UI (org.eclipse.ui.workbench) preferences node id */

	/**
	 * Workbench section for dialog settings
	 */

	/**
	 * The name of the dialog settings file (value
	 * <code>"dialog_settings.xml"</code>).
	 */

	/**
	 * Key used to allow dialog_settings.xml customization. The value is the root
	 * url of the parent directory containing settings for different plug-ins. Each
	 * plug-in dialog_settings.xml file should reside in the directory with the
	 * plug-in name.
	 */

	private Bundle fBundle;

	private IDialogSettings fDialogSettings;

	DialogSettingsProvider(Bundle bundle) {
		fBundle = bundle;
	}

	/**
	 * Loads and returns the dialog settings for the bundle of the passed class. The
	 * implementation first looks for a standard named file in the plug-in's
	 * read/write state area; if no such file exists, default product dialog
	 * settings directory (specified by
	 * org.eclipse.ui/default_dialog_settings_rootUrl property) is checked to see if
	 * a file with default bundle dialog settings exists; if no such file exists,
	 * the bundle's install directory is checked to see if one was installed with
	 * some default settings; if no file is found in either place, a new empty
	 * dialog settings is created. If a problem occurs, an empty settings is used
	 * without throwing an exception.
	 * <p>
	 * When the bundle stops, e.g. by shutting down the application, the dialog
	 * settings are saved automatically.
	 * <p>
	 *
	 * @param bundle the bundle for which the dialog settings must be loaded
	 * @return the {@link IDialogSettings} which may be empty but are never null
	 *
	 * @see #saveDialogSettings(Bundle)
	 */
	private static IDialogSettings loadDialogSettings(Bundle bundle) {
								.orElseGet(() -> createEmptySettings())));
		return dialogSettings;
	}

	/**
	 * @return true if the product specific settings file was successfully read
	 */
	private static Optional<IDialogSettings> loadDefaultDialogSettingsFromProduct(Bundle bundle) {
		IPreferencesService preferencesService = Platform.getPreferencesService();
		Preferences node = preferencesService.getRootNode().node(INSTANCE_SCOPE).node(ORG_ECLIPSE_UI);
		String rootUrl = node.get(KEY_DEFAULT_DIALOG_SETTINGS_ROOTURL, ""); //$NON-NLS-1$

		if (rootUrl == null || rootUrl.isEmpty()) {
			node = preferencesService.getRootNode().node(DEFAULT_SCOPE).node(ORG_ECLIPSE_UI);
			rootUrl = node.get(KEY_DEFAULT_DIALOG_SETTINGS_ROOTURL, ""); //$NON-NLS-1$
			if (rootUrl == null || rootUrl.isEmpty()) {
				return Optional.empty();
			}
		}
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
		} catch (FileNotFoundException e) {
			return Optional.empty();
		} catch (IOException e) {
			Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to load dialog settings from: " + fullUrl, e)); //$NON-NLS-1$
			return Optional.empty();
		}

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
			dialogSettings.load(reader);
			return Optional.of(dialogSettings);
		} catch (IOException e) {
			Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to load dialog settings from: " + url, e)); //$NON-NLS-1$
		}
		return Optional.empty();
	}

	/**
	 * @return {@link IDialogSettings} if the workspace settings file was
	 *         successfully read, null otherwise.
	 */

	private static Optional<IDialogSettings> loadDialogSettingsFromWorkspace(Bundle bundle) {
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

	private static Optional<IDialogSettings> loadDefaultDialogSettingsFromBundle(Bundle bundle) {
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

	private static IPath getStateLocationOrNull(Bundle bundle) {
		try {
			return Platform.getStateLocation(bundle);
		} catch (IllegalStateException e) {
			Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(),
					"Failed to get state location for bundle: " + bundle, e)); //$NON-NLS-1$
			return null;
		}
	}

	@Override
	public synchronized IDialogSettings loadDialogSettings() {
		fDialogSettings = loadDialogSettings(fBundle);
		return fDialogSettings;
	}

	@Override
	public synchronized void saveDialogSettings() {
		saveDialogSettings(fDialogSettings, fBundle);
	}

	@Override
	public IDialogSettings getDialogSettings() {
		if (fDialogSettings == null) {
			loadDialogSettings();
		}
		return fDialogSettings;
	}

	/**
	 * Saves this plug-in's dialog settings. Any problems which arise are logged.
	 *
	 * @param bundle the bundle to save the dialog settings for
	 */
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
					"No state location. Failed to save dialog settings for bundle: " + bundle.getBundleId(), e)); //$NON-NLS-1$
		}
	}
}
