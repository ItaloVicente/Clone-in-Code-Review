
package org.eclipse.jgit.diffmergetool;

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

public class CommandExecutor {

	private FS fs;

	private boolean checkExitCode;

	private File commandFile;

	private boolean useMsys2;

	public CommandExecutor(FS fs
		this.fs = fs;
		this.checkExitCode = checkExitCode;
	}

	public ExecutionResult run(String command
			Map<String
			throws ToolException
		String[] commandArray = createCommandArray(command);
		try {
			ProcessBuilder pb = fs.runInShell(commandArray[0]
					Arrays.copyOfRange(commandArray
			pb.directory(workingDir);
			Map<String
			if (env != null) {
				envp.putAll(env);
			}
			ExecutionResult result = fs.execute(pb
			int rc = result.getRc();
			if (rc != 0) {
				boolean execError = isCommandExecutionError(rc);
				if (checkExitCode || execError) {
					throw new ToolException(
							new String(result.getStderr().toByteArray())
							result
				}
			}
			return result;
		} finally {
			deleteCommandArray();
		}
	}

	public boolean checkExecutable(String path
			Map<String
			throws ToolException
		checkUseMsys2(path);
		String command = null;
		if (fs instanceof FS_Win32 && !useMsys2) {
			Path p = Paths.get(path);
			if (p.isAbsolute() && Files.exists(p) && Files.isExecutable(p)) {
				return true;
			}
		} else {
		}
		boolean available = true;
		try {
			ExecutionResult rc = run(command
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
			throws ToolException
		String[] commandArray = null;
		checkUseMsys2(command);
		createCommandFile(command);
		if (fs instanceof FS_POSIX) {
			commandArray = new String[1];
			commandArray[0] = commandFile.getCanonicalPath();
		} else if (fs instanceof FS_Win32) {
			if (useMsys2) {
				commandArray = new String[3];
				commandArray[2] = commandFile.getCanonicalPath().replace("\\"
			} else {
				commandArray = new String[1];
				commandArray[0] = commandFile.getCanonicalPath();
			}
		} else if (fs instanceof FS_Win32_Cygwin) {
			commandArray = new String[1];
			commandArray[0] = commandFile.getCanonicalPath().replace("\\"
		} else {
			throw new ToolException(
		}
		return commandArray;
	}

	private void checkUseMsys2(String command) {
		useMsys2 = false;
		if (useMsys2Str != null && !useMsys2Str.isEmpty()) {
			} else {
				useMsys2 = Boolean.parseBoolean(useMsys2Str);
			}
		}
	}

	private void createCommandFile(String command)
			throws ToolException
		String fileExtension = null;
		if (useMsys2 || fs instanceof FS_POSIX
				|| fs instanceof FS_Win32_Cygwin) {
		} else if (fs instanceof FS_Win32) {
		} else {
			throw new ToolException(
		}
		commandFile = File.createTempFile(".__"
		try (OutputStream outStream = new FileOutputStream(commandFile)) {
			byte[] strToBytes = command.getBytes();
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
