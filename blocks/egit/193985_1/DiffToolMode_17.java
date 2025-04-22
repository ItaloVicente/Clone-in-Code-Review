package org.eclipse.egit.ui.internal.diffmerge;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.core.runtime.Platform;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.internal.diffmergetool.DiffTools;
import org.eclipse.jgit.internal.diffmergetool.MergeTools;
import org.eclipse.jgit.internal.diffmergetool.ToolException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.SystemReader;

public final class DiffMergeSettings {

	public static DiffToolMode getDiffToolMode() {
		return DiffToolMode
				.fromInt(getStore().getInt(UIPreferences.DIFF_TOOL_MODE));
	}

	public static MergeToolMode getMergeToolMode() {
		return MergeToolMode
				.fromInt(getStore().getInt(UIPreferences.MERGE_TOOL_MODE));
	}

	public static boolean useInternalDiffTool() {
		return getStore()
				.getInt(UIPreferences.DIFF_TOOL_MODE) == DiffToolMode.INTERNAL
						.getValue();
	}

	public static boolean useInternalMergeTool() {
		return getStore()
				.getInt(UIPreferences.MERGE_TOOL_MODE) == MergeToolMode.INTERNAL
						.getValue();
	}

	public static String getMergeToolName() {
		return getStore().getString(UIPreferences.MERGE_TOOL_CUSTOM);
	}

	private static String getDiffToolName() {
		return getStore().getString(UIPreferences.DIFF_TOOL_CUSTOM);
	}

	public static Set<String> getAvailableDiffTools() {
		DiffTools diffToolManager = new DiffTools(loadUserConfig());
		return diffToolManager.getPredefinedAvailableTools();
	}

	public static Set<String> getAvailableMergeTools() {
		MergeTools mergeToolManager = new MergeTools(loadUserConfig());
		return mergeToolManager.getPredefinedAvailableTools();
	}

	public static Optional<String> getDiffToolName(Repository repository,
			String relativeFilePath) {
		DiffToolMode diffToolMode = getDiffToolMode();
		Optional<String> toolName = Optional.empty();
		if (diffToolMode == DiffToolMode.INTERNAL) {
			return toolName;
		}
		if (diffToolMode == DiffToolMode.EXTERNAL_FOR_TYPE
				|| diffToolMode == DiffToolMode.GIT_CONFIG) {
			try {
				toolName = new DiffTools(repository)
						.getExternalToolFromAttributes(relativeFilePath);
			} catch (ToolException e) {
				Activator.handleError(
						UIText.CompareUtils_GitConfigurationErrorText, e, true);
			}
		}

		if (!toolName.isPresent() && diffToolMode == DiffToolMode.GIT_CONFIG) {
			toolName = DiffMergeSettings.readExternalToolFromGitConfig(
					c -> getDiffToolFromGitConfig(c), repository);
		}

		if (diffToolMode == DiffToolMode.EXTERNAL) {
			toolName = Optional.of(getDiffToolName());
		}

		return toolName;
	}

	public static Optional<String> getMergeToolName(Repository repository,
			String relativeFilePath) {

		MergeToolMode mergeToolMode = getMergeToolMode();
		Optional<String> toolName = Optional.empty();
		if (mergeToolMode == MergeToolMode.INTERNAL) {
			return toolName;
		}
		if (mergeToolMode == MergeToolMode.EXTERNAL_FOR_TYPE
				|| mergeToolMode == MergeToolMode.GIT_CONFIG) {
			try {
				toolName = new MergeTools(repository)
						.getExternalToolFromAttributes(relativeFilePath);
			} catch (ToolException e) {
				Activator.handleError(
						UIText.CompareUtils_GitConfigurationErrorText, e, true);
			}
		}

		if (!toolName.isPresent()
				&& mergeToolMode == MergeToolMode.GIT_CONFIG) {
			toolName = DiffMergeSettings
					.readExternalToolFromGitConfig(
							c -> getMergeToolFromGitConfig(c), repository);
			if (toolName.isEmpty()) {
				toolName = Optional.of(""); //$NON-NLS-1$
				Activator.handleError(
						UIText.MergeToolActionHandler_noToolConfiguredDialogTitle,
						null, true);
			}
		}

		if (mergeToolMode == MergeToolMode.EXTERNAL) {
			toolName = Optional.of(getMergeToolName());
		}

		return toolName;
	}

	private static Optional<String> readExternalToolFromGitConfig(
			Function<StoredConfig, String> readConfiguredExternalTool,
			Repository repository) {
		StoredConfig repoConfig = repository.getConfig();
		String externalTool = readConfiguredExternalTool.apply(repoConfig);
		return Optional.ofNullable(externalTool);
	}



	private static String getDiffToolFromGitConfig(StoredConfig config) {
		DiffTools diffToolManager = new DiffTools(config);
		return diffToolManager.getDefaultToolName(false);
	}

	private static String getMergeToolFromGitConfig(StoredConfig config) {
		MergeTools mergeToolManager = new MergeTools(config);
		return mergeToolManager.getDefaultToolName(false);
	}

	private static FileBasedConfig loadUserConfig() {
		FileBasedConfig config = SystemReader.getInstance().openUserConfig(null,
				FS.DETECTED);
		try {
			config.load();
		} catch (IOException | ConfigInvalidException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		return config;
	}

	private static IPreferenceStore getStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	@Nullable
	public static String getDiffToolCommandFromPreferences(String filePath) {
		DiffToolMode diffToolMode = getDiffToolMode();
		if (diffToolMode == DiffToolMode.EXTERNAL_FOR_TYPE) {
			String fileExtension = getFileExtension(filePath);
			if (!StringUtils.isEmptyOrNull(fileExtension)) {
				String preference = getExternalDiffToolPreference();
				String[] tools = preference.split(","); //$NON-NLS-1$
				for (int i = 0; i < tools.length; i += 2) {
					String extension = tools[i].trim();
					String command = tools[i + 1].trim();
					if (Objects.equals(extension, fileExtension)) {
						return command;
					}
				}
			}
		}
		return null;
	}

	public static String getExternalDiffToolPreference() {
		String preference = Platform.getPreferencesService().getString(
				Activator.PLUGIN_ID,
				UIPreferences.EXTERNAL_DIFF_TOOL_FOR_EXTENSION,
				"", //$NON-NLS-1$
				null);
		return preference;
	}

	public static String getFileExtension(String path) {
		int index = path.lastIndexOf('.');
		if (index == -1) {
			return ""; //$NON-NLS-1$
		}
		if (index == (path.length() - 1)) {
			return ""; //$NON-NLS-1$
		}
		return path.substring(index + 1);
	}
}
