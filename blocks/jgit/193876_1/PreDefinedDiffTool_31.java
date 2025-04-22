package org.eclipse.jgit.internal.diffmergetool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jgit.internal.diffmergetool.FileElement.Type;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.internal.BooleanTriState;
import org.eclipse.jgit.util.FS.ExecutionResult;

public class MergeTools {

	Repository repo;

	private final MergeToolConfig config;

	private final Map<String

	private final Map<String

	public MergeTools(Repository repo) {
		this.repo = repo;
		config = repo.getConfig().get(MergeToolConfig.KEY);
		predefinedTools = setupPredefinedTools();
		userDefinedTools = setupUserDefinedTools(config
	}

	public ExecutionResult merge(FileElement localFile
			FileElement mergedFile
			String toolName
			throws ToolException {
		ExternalMergeTool tool = guessTool(toolName
		FileElement backup = null;
		ExecutionResult result = null;
		try {
			File workingDir = repo.getWorkTree();
			backup = createBackupFile(mergedFile.getPath()
					tempDir != null ? tempDir : workingDir);
			boolean trust = tool.getTrustExitCode() == BooleanTriState.TRUE;
			String command = ExternalToolUtils.prepareCommand(
					tool.getCommand(baseFile != null)
					mergedFile
			Map<String
					localFile
			CommandExecutor cmdExec = new CommandExecutor(repo.getFS()
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

	private FileElement createBackupFile(String filePath
			throws IOException {
		FileElement backup = null;
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			backup = new FileElement(filePath
			Files.copy(path
					StandardCopyOption.REPLACE_EXISTING);
		}
		return backup;
	}

	public File createTempDirectory() throws IOException {
		return config.isWriteToTemp()
				: null;
	}

	public Set<String> getToolNames() {
		return config.getToolNames();
	}

	public Map<String
		return userDefinedTools;
	}

	public Map<String
		return predefinedTools;
	}

	public Map<String
		return new TreeMap<>();
	}

	public String getDefaultToolName(BooleanTriState gui) {
				: config.getDefaultToolName();
	}

	public boolean isInteractive() {
		return config.isPrompt();
	}

	private ExternalMergeTool guessTool(String toolName
			throws ToolException {
		if ((toolName == null) || toolName.isEmpty()) {
			toolName = getDefaultToolName(gui);
		}
		ExternalMergeTool tool = getTool(toolName);
		if (tool == null) {
		}
		return tool;
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
			MergeToolConfig cfg
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
