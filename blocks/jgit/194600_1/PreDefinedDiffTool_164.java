package org.eclipse.jgit.internal.diffmergetool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.diffmergetool.FileElement.Type;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.lib.internal.BooleanTriState;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.FS.ExecutionResult;

public class MergeTools {

	private final FS fs;

	private final File gitDir;

	private final File workTree;

	private final MergeToolConfig config;

	private final Repository repo;

	private final Map<String

	private final Map<String

	public MergeTools(Repository repo) {
		this(repo
	}

	public MergeTools(StoredConfig config) {
		this(null
	}

	private MergeTools(Repository repo
		this.repo = repo;
		this.config = config.get(MergeToolConfig.KEY);
		this.gitDir = repo == null ? null : repo.getDirectory();
		this.fs = repo == null ? FS.DETECTED : repo.getFS();
		this.workTree = repo == null ? null : repo.getWorkTree();
		predefinedTools = setupPredefinedTools();
		userDefinedTools = setupUserDefinedTools(predefinedTools);
	}

	public Optional<ExecutionResult> merge(FileElement localFile
			FileElement remoteFile
			FileElement baseFile
			BooleanTriState prompt
			PromptContinueHandler promptHandler
			InformNoToolHandler noToolHandler) throws ToolException {

		String toolNameToUse;

		if (toolName == null) {
			throw new ToolException(JGitText.get().diffToolNullError);
		}

		if (toolName.isPresent()) {
			toolNameToUse = toolName.get();
		} else {
			toolNameToUse = getDefaultToolName(gui);

			if (StringUtils.isEmptyOrNull(toolNameToUse)) {
				noToolHandler.inform(new ArrayList<>(predefinedTools.keySet()));
				toolNameToUse = getFirstAvailableTool();
			}
		}

		if (StringUtils.isEmptyOrNull(toolNameToUse)) {
			throw new ToolException(JGitText.get().diffToolNotGivenError);
		}

		boolean doPrompt;
		if (prompt != BooleanTriState.UNSET) {
			doPrompt = prompt == BooleanTriState.TRUE;
		} else {
			doPrompt = isInteractive();
		}

		if (doPrompt) {
			if (!promptHandler.prompt(toolNameToUse)) {
				return Optional.empty();
			}
		}

		ExternalMergeTool tool = getTool(toolNameToUse);
		if (tool == null) {
			throw new ToolException(
		}

		return Optional.of(merge(localFile
				tempDir
	}

	public ExecutionResult merge(FileElement localFile
			FileElement mergedFile
			ExternalMergeTool tool) throws ToolException {
		FileElement backup = null;
		ExecutionResult result = null;
		try {
			backup = createBackupFile(mergedFile
					tempDir != null ? tempDir : workTree);
			String command = ExternalToolUtils.prepareCommand(
					tool.getCommand(baseFile != null)
					mergedFile
			Map<String
					gitDir
			boolean trust = tool.getTrustExitCode() == BooleanTriState.TRUE;
			CommandExecutor cmdExec = new CommandExecutor(fs
			result = cmdExec.run(command
			if (backup != null) {
				keepBackupFile(mergedFile.getPath()
			}
			return result;
		} catch (IOException | InterruptedException e) {
			throw new ToolException(e);
		} finally {
			if (backup != null) {
				backup.cleanTemporaries();
			}
			if (!((result == null) && config.isKeepTemporaries())) {
				localFile.cleanTemporaries();
				remoteFile.cleanTemporaries();
				if (baseFile != null) {
					baseFile.cleanTemporaries();
				}
				if (config.isWriteToTemp() && (tempDir != null)
						&& tempDir.exists()) {
					tempDir.delete();
				}
			}
		}
	}

	private FileElement createBackupFile(FileElement from
			throws IOException {
		FileElement backup = null;
		Path path = Paths.get(from.getPath());
		if (Files.exists(path)) {
			backup = new FileElement(from.getPath()
			Files.copy(path
					StandardCopyOption.REPLACE_EXISTING);
		}
		return backup;
	}

	public File createTempDirectory() throws IOException {
		return config.isWriteToTemp()
				: null;
	}

	public Set<String> getUserDefinedToolNames() {
		return userDefinedTools.keySet();
	}

	public Set<String> getPredefinedToolNames() {
		return predefinedTools.keySet();
	}

	public Set<String> getAllToolNames() {
		String defaultName = getDefaultToolName(false);
		if (defaultName == null) {
			defaultName = getFirstAvailableTool();
		}
		return ExternalToolUtils.createSortedToolSet(defaultName
				getUserDefinedToolNames()
	}

	public Optional<String> getExternalToolFromAttributes(final String path)
			throws ToolException {
		return ExternalToolUtils.getExternalToolFromAttributes(repo
				ExternalToolUtils.KEY_MERGE_TOOL);
	}

	public Set<String> getPredefinedAvailableTools() {
		Map<String
		Set<String> availableTools = new LinkedHashSet<>();
		for (Entry<String
			if (elem.getValue().isAvailable()) {
				availableTools.add(elem.getKey());
			}
		}
		return availableTools;
	}

	public Map<String
		return Collections.unmodifiableMap(userDefinedTools);
	}

	public Map<String
			boolean checkAvailability) {
		if (checkAvailability) {
			for (ExternalMergeTool tool : predefinedTools.values()) {
				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) tool;
				predefTool.setAvailable(ExternalToolUtils.isToolAvailable(fs
						gitDir
			}
		}
		return Collections.unmodifiableMap(predefinedTools);
	}

	public String getFirstAvailableTool() {
		String name = null;
		for (ExternalMergeTool tool : predefinedTools.values()) {
			if (ExternalToolUtils.isToolAvailable(fs
					tool.getPath())) {
				name = tool.getName();
				break;
			}
		}
		return name;
	}

	public boolean isInteractive() {
		return config.isPrompt();
	}

	public String getDefaultToolName(boolean gui) {
		return gui ? config.getDefaultGuiToolName()
				: config.getDefaultToolName();
	}

	private ExternalMergeTool getTool(final String name) {
		ExternalMergeTool tool = userDefinedTools.get(name);
		if (tool == null) {
			tool = predefinedTools.get(name);
		}
		return tool;
	}

	private void keepBackupFile(String mergedFilePath
			throws IOException {
		if (config.isKeepBackup()) {
			Path backupPath = backup.getFile().toPath();
			Files.move(backupPath
					backupPath.resolveSibling(
							Paths.get(mergedFilePath).getFileName() + ".orig")
					StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private Map<String
		Map<String
		for (CommandLineMergeTool tool : CommandLineMergeTool.values()) {
			tools.put(tool.name()
		}
		return tools;
	}

	private Map<String
			Map<String
		Map<String
		Map<String
		for (String name : userTools.keySet()) {
			ExternalMergeTool userTool = userTools.get(name);
			if (userTool.getCommand() != null) {
				tools.put(name
			} else if (userTool.getPath() != null) {
				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) predefTools
						.get(name);
				if (predefTool != null) {
					predefTool.setPath(userTool.getPath());
					if (userTool.getTrustExitCode() != BooleanTriState.UNSET) {
						predefTool
								.setTrustExitCode(userTool.getTrustExitCode());
					}
				}
			}
		}
		return tools;
	}

}
