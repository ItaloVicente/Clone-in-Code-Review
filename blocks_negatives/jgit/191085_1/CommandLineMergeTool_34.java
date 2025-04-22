/*
 * Copyright (C) 2018-2021, Andre Bossert <andre.bossert@siemens.com>
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.eclipse.jgit.internal.diffmergetool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.eclipse.jgit.util.FS_POSIX;
import org.eclipse.jgit.util.FS_Win32;
import org.eclipse.jgit.util.FS_Win32_Cygwin;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.SystemReader;

/**
 * Runs a command with help of FS.
 */
public class CommandExecutor {

	private FS fs;

	private boolean checkExitCode;

	private File commandFile;

	private boolean useMsys2;

	/**
	 * @param fs
	 *            the file system
	 * @param checkExitCode
	 *            should the exit code be checked for errors ?
	 */
	public CommandExecutor(FS fs, boolean checkExitCode) {
		this.fs = fs;
		this.checkExitCode = checkExitCode;
	}

	/**
	 * @param command
	 *            the command string
	 * @param workingDir
	 *            the working directory
	 * @param env
	 *            the environment
	 * @return the execution result
	 * @throws ToolException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public ExecutionResult run(String command, File workingDir,
			Map<String, String> env)
			throws ToolException, IOException, InterruptedException {
		String[] commandArray = createCommandArray(command);
		try {
			ProcessBuilder pb = fs.runInShell(commandArray[0],
					Arrays.copyOfRange(commandArray, 1, commandArray.length));
			pb.directory(workingDir);
			Map<String, String> envp = pb.environment();
			if (env != null) {
				envp.putAll(env);
			}
			ExecutionResult result = fs.execute(pb, null);
			int rc = result.getRc();
			if (rc != 0) {
				boolean execError = isCommandExecutionError(rc);
				if (checkExitCode || execError) {
					throw new ToolException(
									+ new String(
											result.getStderr().toByteArray(),
											SystemReader.getInstance()
													.getDefaultCharset()),
							result, execError);
				}
			}
			return result;
		} finally {
			deleteCommandArray();
		}
	}

	/**
	 * @param path
	 *            the executable path
	 * @param workingDir
	 *            the working directory
	 * @param env
	 *            the environment
	 * @return the execution result
	 * @throws ToolException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public boolean checkExecutable(String path, File workingDir,
			Map<String, String> env)
			throws ToolException, IOException, InterruptedException {
		checkUseMsys2(path);
		String command = null;
		if (fs instanceof FS_Win32 && !useMsys2) {
			Path p = Paths.get(path);
			if (p.isAbsolute() && Files.isExecutable(p)) {
				return true;
			}
		} else {
		}
		boolean available = true;
		try {
			ExecutionResult rc = run(command, workingDir, env);
			if (rc.getRc() != 0) {
				available = false;
			}
		} catch (IOException | InterruptedException | NoWorkTreeException
				| ToolException e) {
		}
		return available;
	}

	private void deleteCommandArray() {
		deleteCommandFile();
	}

	private String[] createCommandArray(String command)
			throws ToolException, IOException {
		String[] commandArray = null;
		checkUseMsys2(command);
		createCommandFile(command);
		if (fs instanceof FS_POSIX) {
			commandArray = new String[1];
			commandArray[0] = commandFile.getCanonicalPath();
		} else if (fs instanceof FS_Win32) {
			if (useMsys2) {
				commandArray = new String[3];
				commandArray[2] = commandFile.getCanonicalPath().replace("\\", //$NON-NLS-1$
			} else {
				commandArray = new String[1];
				commandArray[0] = commandFile.getCanonicalPath();
			}
		} else if (fs instanceof FS_Win32_Cygwin) {
			commandArray = new String[1];
			commandArray[0] = commandFile.getCanonicalPath().replace("\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			throw new ToolException(
		}
		return commandArray;
	}

	private void checkUseMsys2(String command) {
		useMsys2 = false;
		if (!StringUtils.isEmptyOrNull(useMsys2Str)) {
			} else {
				useMsys2 = Boolean.parseBoolean(useMsys2Str);
			}
		}
	}

	private void createCommandFile(String command)
			throws ToolException, IOException {
		String fileExtension = null;
		if (useMsys2 || fs instanceof FS_POSIX
				|| fs instanceof FS_Win32_Cygwin) {
		} else if (fs instanceof FS_Win32) {
		} else {
			throw new ToolException(
		}
		commandFile = File.createTempFile(".__", //$NON-NLS-1$
		try (OutputStream outStream = new FileOutputStream(commandFile)) {
			byte[] strToBytes = command
					.getBytes(SystemReader.getInstance().getDefaultCharset());
			outStream.write(strToBytes);
			outStream.close();
		}
		commandFile.setExecutable(true);
	}

	private void deleteCommandFile() {
		if (commandFile != null && commandFile.exists()) {
			commandFile.delete();
		}
	}

	private boolean isCommandExecutionError(int rc) {
		if (useMsys2 || fs instanceof FS_POSIX
				|| fs instanceof FS_Win32_Cygwin) {
			if ((rc == 126) || (rc == 127)) {
				return true;
			}
		}
		else if (fs instanceof FS_Win32) {
			if (rc == 9009) {
				return true;
			}
		}
		return false;
	}

}
