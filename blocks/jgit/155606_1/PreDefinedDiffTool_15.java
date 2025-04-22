
package org.eclipse.jgit.diffmergetool;

import java.util.TreeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jgit.diffmergetool.FileElement.Type;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;

public class MergeToolManager {

	private final FS fs;

	private final File gitDir;

	private final File workTree;

	private final MergeToolConfig config;

	private final Map<String

	private final Map<String

	public MergeToolManager(Repository db) {
		this(db.getFS()
	}

	public MergeToolManager(FS fs
			StoredConfig userConfig) {
		this.fs = fs;
		this.gitDir = gitDir;
		this.workTree = workTree;
		this.config = userConfig.get(MergeToolConfig.KEY);
		predefinedTools = setupPredefinedTools();
		userDefinedTools = setupUserDefinedTools(config
	}

	public Optional<ExecutionResult> merge(FileElement localFile
			FileElement remoteFile
			FileElement baseFile
			Optional<Boolean> prompt
			PromptContinueHandler promptHandler
			InformNoToolHandler noToolHandler) throws ToolException {

		String toolNameToUse;

		if (toolName.isPresent()) {
			toolNameToUse = toolName.get();
		} else {
			toolNameToUse = getDefaultToolName(gui);

			if (toolNameToUse == null || toolNameToUse.isEmpty()) {
				noToolHandler.inform(new ArrayList<>(predefinedTools.keySet()));
				toolNameToUse = getFirstAvailableTool();
			}
		}

		@SuppressWarnings("boxing")
		boolean doPrompt = prompt.orElse(isPrompt());

		if (doPrompt) {
			if (!promptHandler.prompt(toolNameToUse)) {
				return Optional.empty();
			}
		}

		return Optional.of(
				merge(localFile
						getTool(toolNameToUse)));
	}

	public ExecutionResult merge(FileElement localFile
			FileElement remoteFile
			FileElement baseFile
			throws ToolException {
		FileElement backup = null;
		ExecutionResult result = null;
		try {
			File workingDir = workTree;
			backup = createBackupFile(mergedFile
					tempDir != null ? tempDir : workingDir);
			String command = Utils.prepareCommand(
					tool.getCommand(baseFile != null)
					localFile
			Map<String
					localFile
			boolean trust = tool.getTrustExitCode().toBoolean();
			CommandExecutor cmdExec = new CommandExecutor(fs
			result = cmdExec.run(command
			keepBackupFile(mergedFile.getPath()
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
		FileElement backup = new FileElement(from.getPath()
		Files.copy(from.getFile().toPath()
				backup.createTempFile(toParentDir).toPath()
				StandardCopyOption.REPLACE_EXISTING);
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
		String defaultName = getDefaultToolName(
				BooleanOption.NOT_DEFINED_FALSE);
		if (defaultName == null) {
			defaultName = getFirstAvailableTool();
		}
		return Utils.createSortedToolSet(defaultName
				getPredefinedToolNames());
	}

	public Map<String
		return userDefinedTools;
	}

	public Map<String
			boolean checkAvailability) {
		if (checkAvailability) {
			for (IMergeTool tool : predefinedTools.values()) {
				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) tool;
				predefTool.setAvailable(
						Utils.isToolAvailable(fs
								predefTool.getPath()));
			}
		}
		return predefinedTools;
	}

	public String getFirstAvailableTool() {
		String name = null;
		for (IMergeTool tool : predefinedTools.values()) {
			if (Utils.isToolAvailable(fs
				name = tool.getName();
				break;
			}
		}
		return name;
	}

	public String getDefaultToolName(BooleanOption gui) {
		return gui.toBoolean() ? config.getDefaultGuiToolName()
				: config.getDefaultToolName();
	}

	public boolean isPrompt() {
		return config.isPrompt();
	}

	public String getDefaultToolName(boolean gui) {
		return gui ? config.getDefaultGuiToolName()
				: config.getDefaultToolName();
	}

	private IMergeTool getTool(final String name) {
		IMergeTool tool = userDefinedTools.get(name);
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
		for (PreDefinedMergeTools tool : PreDefinedMergeTools.values()) {
			tools
					.put(tool.name()
							new PreDefinedMergeTool(tool.name()
									tool.getParameters(true)
									tool.getParameters(false)
									BooleanOption.defined(
											tool.isExitCodeTrustable())));
		}
		return tools;
	}

	private Map<String
			Map<String
		Map<String
		Map<String
		for (String name : userTools.keySet()) {
			IMergeTool userTool = userTools.get(name);
			if (userTool.getCommand() != null) {
				tools.put(name
			} else if (userTool.getPath() != null) {
				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) predefTools
						.get(name);
				if (predefTool != null) {
					predefTool.setPath(userTool.getPath());
					if (userTool.getTrustExitCode().isDefined()) {
						predefTool
								.setTrustExitCode(userTool.getTrustExitCode());
					}
				}
			}
		}
		return tools;
	}

}
