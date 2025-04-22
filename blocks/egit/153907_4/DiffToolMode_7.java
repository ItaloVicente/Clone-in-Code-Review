package org.eclipse.egit.ui.internal.diffmerge;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jgit.diffmergetool.DiffTools;
import org.eclipse.jgit.diffmergetool.MergeTools;
import org.eclipse.jgit.diffmergetool.ToolException;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS;
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
			toolName = DiffMergeSettings.readDiffToolFromGitConfig();
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
			toolName = DiffMergeSettings.readDiffToolFromGitConfig();
		}

		if (mergeToolMode == MergeToolMode.EXTERNAL) {
			toolName = Optional.of(getDiffToolName());
		}

		return toolName;
	}

	private static Optional<String> readDiffToolFromGitConfig() {
		FileBasedConfig config = loadUserConfig();
		updateDefaultDiffToolFromGitConfig(config);
		return Optional.ofNullable(
				getStore().getString(UIPreferences.DIFF_TOOL_FROM_GIT_CONFIG));
	}

	private static void updateDefaultDiffToolFromGitConfig(
			FileBasedConfig config) {
		String diffTool = getCustomDiffToolFromGitConfig(config);
		if (diffTool != null) {
			getStore().setValue(UIPreferences.DIFF_TOOL_FROM_GIT_CONFIG,
					diffTool);
		} else {
			getStore().setValue(UIPreferences.DIFF_TOOL_FROM_GIT_CONFIG, ""); //$NON-NLS-1$
		}
	}

	private static String getCustomDiffToolFromGitConfig(
			FileBasedConfig config) {
		DiffTools diffToolManager = new DiffTools(config);
		return diffToolManager.getDefaultToolName(false);
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
}
