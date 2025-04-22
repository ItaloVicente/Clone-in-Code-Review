package org.eclipse.egit.ui.internal.gerrit;

import java.util.List;

import org.eclipse.egit.core.internal.gerrit.GerritUtil;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.fetch.FetchGerritChangePage;
import org.eclipse.egit.ui.internal.push.PushToGerritPage;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public final class GerritDialogSettings {

	public static final String FETCH_FROM_GERRIT_SECTION = FetchGerritChangePage.class
			.getSimpleName();

	public static final String PUSH_TO_GERRIT_SECTION = PushToGerritPage.class
			.getSimpleName();

	public static final String LAST_URI_SUFFIX = ".lastUri"; //$NON-NLS-1$

	private GerritDialogSettings() {
	}

	public static void updateRemoteConfig(Repository repository,
			RemoteConfig config) {
		if (repository == null || config == null) {
			return;
		}
		if (GerritUtil.isGerritFetch(config)) {
			updateGerritFetch(repository, config);
		}
		if (GerritUtil.isGerritPush(config)) {
			updateGerritPush(repository, config);
		}
	}

	public static @NonNull IDialogSettings getSection(String id) {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(id);
		if (section == null) {
			section = settings.addNewSection(id);
			if (section == null) {
				throw new NullPointerException(
						"IDialogSettings section could not be created"); //$NON-NLS-1$
			}
		}
		return section;
	}

	private static void updateGerritFetch(@NonNull Repository repository,
			@NonNull RemoteConfig config) {
		IDialogSettings section = getSection(FETCH_FROM_GERRIT_SECTION);
		String configured = section.get(repository + LAST_URI_SUFFIX);
		if (configured == null || configured.isEmpty()) {
			List<URIish> fetchUris = config.getURIs();
			if (!fetchUris.isEmpty()) {
				section.put(repository + LAST_URI_SUFFIX,
						fetchUris.get(0).toPrivateString());
			}
		}
	}

	private static void updateGerritPush(@NonNull Repository repository,
			@NonNull RemoteConfig config) {
		IDialogSettings section = getSection(PUSH_TO_GERRIT_SECTION);
		String configured = section.get(repository + LAST_URI_SUFFIX);
		if (configured == null || configured.isEmpty()) {
			List<URIish> pushUris = config.getPushURIs();
			if (!pushUris.isEmpty()) {
				section.put(repository + LAST_URI_SUFFIX,
						pushUris.get(0).toPrivateString());
			}
		}
	}
}
