/*
 * Copyright (C) 2018-2020, Andre Bossert <andre.bossert@siemens.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.eclipse.jgit.diffmergetool;

import java.util.TreeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.diffmergetool.FileElement.Type;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS.ExecutionResult;

/**
 * Manages merge tools.
 *
 * @since 5.7
 */
public class MergeToolManager {

	Repository db;

	private final MergeToolConfig config;

	private final Map<String, ExternalMergeTool> predefinedTools;

	private final Map<String, ExternalMergeTool> userDefinedTools;

	/**
	 * @param db the repository database
	 */
	public MergeToolManager(Repository db) {
		this.db = db;
		config = db.getConfig().get(MergeToolConfig.KEY);
		predefinedTools = setupPredefinedTools();
		userDefinedTools = setupUserDefinedTools(config, predefinedTools);
	}

	/**
	 * @param localFile
	 *            the local file element
	 * @param remoteFile
	 *            the remote file element
	 * @param mergedFile
	 *            the merged file element
	 * @param baseFile
	 *            the base file element (can be null)
	 * @param tempDir
	 *            the temporary directory (needed for backup and auto-remove,
	 *            can be null)
	 * @param toolName
	 *            the selected tool name (can be null)
	 * @param prompt
	 *            the prompt option
	 * @param gui
	 *            the GUI option
	 * @return the execution result from tool
	 * @throws ToolException
	 */
	public ExecutionResult merge(FileElement localFile,
			FileElement remoteFile, FileElement mergedFile,
			FileElement baseFile, File tempDir, String toolName,
			BooleanOption prompt,
			BooleanOption gui)
			throws ToolException {
		ExternalMergeTool tool = guessTool(toolName, gui);
		FileElement backup = null;
		ExecutionResult result = null;
		try {
			File workingDir = db.getWorkTree();
			backup = createBackupFile(mergedFile.getPath(),
					tempDir != null ? tempDir : workingDir);
			String command = ExternalToolUtils.prepareCommand(
					tool.getCommand(baseFile != null),
					localFile, remoteFile, mergedFile, baseFile);
			Map<String, String> env = ExternalToolUtils.prepareEnvironment(db, localFile,
					remoteFile, mergedFile, baseFile);
			boolean trust = tool.getTrustExitCode().toBoolean();
			CommandExecutor cmdExec = new CommandExecutor(db.getFS(), trust);
			result = cmdExec.run(command, workingDir, env);
			keepBackupFile(mergedFile.getPath(), backup);
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

	private FileElement createBackupFile(String filePath, File parentDir)
			throws IOException {
		FileElement backup = new FileElement(filePath, Type.BACKUP);
		Files.copy(Paths.get(filePath),
				backup.createTempFile(parentDir).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
		return backup;
	}

	/**
	 * @return the created temporary directory if (mergetol.writeToTemp == true)
	 *         or null if not configured or false.
	 * @throws IOException
	 */
	public File createTempDirectory() throws IOException {
		return config.isWriteToTemp()
				: null;
	}

	/**
	 * @return the tool names
	 */
	public Set<String> getToolNames() {
		return config.getToolNames();
	}

	/**
	 * @return the user defined tools
	 */
	public Map<String, ExternalMergeTool> getUserDefinedTools() {
		return userDefinedTools;
	}

	/**
	 * @param checkAvailability
	 *            true: for checking if tools can be executed; ATTENTION: this
	 *            check took some time, do not execute often (store the map for
	 *            other actions); false: availability is NOT checked:
	 *            isAvailable() returns default false is this case!
	 * @return the predefined tools with optionally checked availability (long
	 *         running operation)
	 */
	public Map<String, ExternalMergeTool> getPredefinedTools(
			boolean checkAvailability) {
		if (checkAvailability) {
			for (ExternalMergeTool tool : predefinedTools.values()) {
				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) tool;
				predefTool.setAvailable(
						ExternalToolUtils.isToolAvailable(db,
								predefTool.getPath()));
			}
		}
		return predefinedTools;
	}

	/**
	 * @return the name of first available predefined tool or null
	 */
	public String getFirstAvailableTool() {
		String name = null;
		for (ExternalMergeTool tool : predefinedTools.values()) {
			if (ExternalToolUtils.isToolAvailable(db, tool.getPath())) {
				name = tool.getName();
				break;
			}
		}
		return name;
	}

	/**
	 * @param gui
	 *            use the diff.guitool setting ?
	 * @return the default tool name
	 */
	public String getDefaultToolName(BooleanOption gui) {
		return gui.toBoolean() ? config.getDefaultGuiToolName()
				: config.getDefaultToolName();
	}

	/**
	 * @return id prompt enabled?
	 */
	public boolean isPrompt() {
		return config.isPrompt();
	}

	private ExternalMergeTool guessTool(String toolName, BooleanOption gui)
			throws ToolException {
		if ((toolName == null) || toolName.isEmpty()) {
			toolName = getDefaultToolName(gui);
		}
		ExternalMergeTool tool = null;
		if ((toolName != null) && !toolName.isEmpty()) {
			tool = getTool(toolName);
		}
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

	private void keepBackupFile(String mergedFilePath, FileElement backup)
			throws IOException {
		if (config.isKeepBackup()) {
			Path backupPath = backup.getFile().toPath();
			Files.move(backupPath,
					backupPath.resolveSibling(
							Paths.get(mergedFilePath).getFileName() + ".orig"), //$NON-NLS-1$
					StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private Map<String, ExternalMergeTool> setupPredefinedTools() {
		Map<String, ExternalMergeTool> tools = new TreeMap<>();
		for (PreDefinedMergeTools tool : PreDefinedMergeTools.values()) {
			tools
					.put(tool.name(),
							new PreDefinedMergeTool(tool.name(), tool.getPath(),
									tool.getParameters(true),
									tool.getParameters(false),
									BooleanOption.toConfigured(
											tool.isExitCodeTrustable())));
		}
		return tools;
	}

	private Map<String, ExternalMergeTool> setupUserDefinedTools(
			MergeToolConfig cfg, Map<String, ExternalMergeTool> predefTools) {
		Map<String, ExternalMergeTool> tools = new TreeMap<>();
		Map<String, ExternalMergeTool> userTools = cfg.getTools();
		for (String name : userTools.keySet()) {
			ExternalMergeTool userTool = userTools.get(name);
			if (userTool.getCommand() != null) {
				tools.put(name, userTool);
			} else if (userTool.getPath() != null) {
				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) predefTools
						.get(name);
				if (predefTool != null) {
					predefTool.setPath(userTool.getPath());
					if (userTool.getTrustExitCode().isConfigured()) {
						predefTool
								.setTrustExitCode(userTool.getTrustExitCode());
					}
				}
			}
		}
		return tools;
	}

}
