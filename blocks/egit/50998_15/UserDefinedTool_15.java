
package org.eclipse.egit.ui.internal.externaltools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.ui.internal.preferences.GitPreferenceRoot;
import org.eclipse.egit.ui.internal.revision.FileRevisionTypedElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class ToolsUtils {

	public static void createAndFillFileForCompare(String filePath,
			InputStream inputStream) {
		FileOutputStream outputStream = createFileForCompare(filePath);
		try {
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public static File createDirectoryForTempFiles() {
		File tempDir = null;
		try {
			tempDir = ToolsUtils.createTempDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempDir;
	}

	public static File createTempDirectory() throws IOException {
		File temp = File.createTempFile("temp", //$NON-NLS-1$
				Long.toString(System.nanoTime()));
		if (!(temp.delete())) {
			throw new IOException(
					"Could not delete temp file: " + temp.getAbsolutePath()); //$NON-NLS-1$
		}
		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: " //$NON-NLS-1$
					+ temp.getAbsolutePath());
		}
		return (temp);
	}

	public static File createSubDirectory(File parent, String subDirName)
			throws IOException {
		File subDir = new File(parent, subDirName);
		if (!(subDir.mkdir())) {
			throw new IOException("Could not create directory: " //$NON-NLS-1$
					+ subDir.getAbsolutePath());
		}
		return (subDir);
	}

	public static void deleteDirectoryForTempFiles(File temp) {
		try {
			deleteTempDirectory(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteTempDirectory(File temp) throws IOException {
		File[] files = temp.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteTempDirectory(f);
				} else {
					f.delete();
				}
			}
		}
		if (!(temp.delete())) {
			throw new IOException(
					"Could not delete temp file: " + temp.getAbsolutePath()); //$NON-NLS-1$
		}
	}

	public static int executeTool(File workingDirectory,
			String mergedCompareFilePath,
			String localCompareFilePath, String remoteCompareFilePath,
			String baseCompareFilePath, String toolCmd, File tempDir)
					throws IOException, InterruptedException {
		if (mergedCompareFilePath != null && localCompareFilePath != null
				&& remoteCompareFilePath != null && toolCmd != null) {
			if (baseCompareFilePath == null) {
				baseCompareFilePath = ""; //$NON-NLS-1$
			}
			String osname = System.getProperty("os.name", "") //$NON-NLS-1$ //$NON-NLS-2$
					.toLowerCase();
			int indexLast = toolCmd.indexOf(".sh"); //$NON-NLS-1$
			if (osname.indexOf("windows") != -1 //$NON-NLS-1$
					&& indexLast != -1) {
				return runExternalToolMsysBash(workingDirectory, toolCmd,
						mergedCompareFilePath, localCompareFilePath,
						remoteCompareFilePath, baseCompareFilePath, tempDir);
			} else {
				return runExternalToolNative(workingDirectory, toolCmd,
						mergedCompareFilePath, localCompareFilePath,
						remoteCompareFilePath, baseCompareFilePath);
			}
		}
		return 0;
	}

	public static int runExternalToolNative(File workingDirectory,
			String command,
			String mergedCompareFilePath, String localCompareFilePath,
			String remoteCompareFilePath, String baseCompareFilePath)
					throws IOException, InterruptedException {
		command = command.replace("$MERGED", mergedCompareFilePath); //$NON-NLS-1$
		command = command.replace("$LOCAL", localCompareFilePath); //$NON-NLS-1$
		command = command.replace("$REMOTE", remoteCompareFilePath); //$NON-NLS-1$
		command = command.replace("$BASE", baseCompareFilePath); //$NON-NLS-1$
		Map<String, String> envp = null;
		return runCommand(command, envp, workingDirectory);
	}

	public static int runExternalToolMsysBash(File workingDirectory, String cmd,
			String mergedCompareFilePath, String localCompareFilePath,
			String remoteCompareFilePath, String baseCompareFilePath,
			File reusedTempDir) throws IOException, InterruptedException {
		String command = cmd;
		String cmdFilePath = null;
		File tempDir = null;
		if (reusedTempDir == null) {
			tempDir = createTempDirectory();
		} else {
			tempDir = reusedTempDir;
		}
		File cmdFile = new File(tempDir, "jgit-windows.sh"); //$NON-NLS-1$
		cmdFilePath = cmdFile.getAbsolutePath();
		cmdFilePath = cmdFilePath.replace("\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
		Files.write(cmdFile.toPath(), command.getBytes(),
				StandardOpenOption.CREATE, StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING);
		command = "cmd.exe /C start /wait \"$bash-name\" \"$bash-cmd\" --login -c \"" //$NON-NLS-1$
				+ cmdFilePath + "\""; //$NON-NLS-1$
		command = command.replace("$bash-name", "bash"); //$NON-NLS-1$ //$NON-NLS-2$
		command = command.replace("$bash-cmd", GitPreferenceRoot.getBashPath());//$NON-NLS-1$
		Map<String, String> envp = new HashMap<>();
		envp.put("MERGED", mergedCompareFilePath); //$NON-NLS-1$
		envp.put("LOCAL", localCompareFilePath); //$NON-NLS-1$
		envp.put("REMOTE", remoteCompareFilePath); //$NON-NLS-1$
		envp.put("BASE", baseCompareFilePath); //$NON-NLS-1$
		int exitCode = runCommand(command, envp, workingDirectory);
		deleteTempDirectory(tempDir);
		return exitCode;
	}

	private static FileOutputStream createFileForCompare(String filePath) {
		FileOutputStream fop = null;
		File file;
		file = new File(filePath);
		try {
			fop = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fop;
	}

	private static int runCommand(String command, Map<String, String> envpAdd,
			File workingDirectory)
			throws IOException, InterruptedException {
		int exitCode = 0;
		System.out.println("command: " + command); //$NON-NLS-1$
		String[] cmdarray = command.split("\\s+"); //$NON-NLS-1$
		ProcessBuilder pb = new ProcessBuilder(cmdarray);
		pb.directory(workingDirectory);
		Map<String, String> envp = pb.environment();
		if (envpAdd != null) {
			envp.putAll(envpAdd);
		}
		System.out.println("Waiting for command..."); //$NON-NLS-1$
		Process process = pb.start();
		exitCode = process.waitFor();
		System.out.println("command done."); //$NON-NLS-1$
		return exitCode;
	}

	public static String loadToTempFile(File baseDirPath, String fileName,
			String subName, FileRevisionTypedElement revision,
			boolean writeToTemp) {
		String tempFileName = null;
		if (revision != null) {
			System.out.println("revision.getName: " //$NON-NLS-1$
					+ revision.getName());
			String revisionFilePath = revision.getPath();
			if (baseDirPath != null && revisionFilePath != null) {
				System.out.println("revision.getPath: " //$NON-NLS-1$
						+ revisionFilePath);
				try {
					if (writeToTemp) {
						File subPath = createSubDirectory(baseDirPath, subName);
						tempFileName = subPath.getAbsolutePath()
								+ File.separator + fileName;
					} else {
						tempFileName = baseDirPath.getAbsolutePath()
								+ File.separator + fileName + "." //$NON-NLS-1$
								+ subName;
					}
					createAndFillFileForCompare(tempFileName,
							revision.getContents());
				} catch (CoreException | IOException e) {
					e.printStackTrace();
				}
				System.out.println("tempFileName: " //$NON-NLS-1$
						+ tempFileName);
			}
		}
		return tempFileName;
	}

	public static int askUserAboutToolExecution(String textHeader,
			String message) {
		MessageBox mbox = new MessageBox(Display.getCurrent().getActiveShell(),
				SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
		mbox.setText(textHeader);
		mbox.setMessage(message);
		return mbox.open();
	}

	public static int informUserAboutError(String textHeader, String message) {
		MessageBox mbox = new MessageBox(Display.getCurrent().getActiveShell(),
				SWT.ICON_ERROR | SWT.OK);
		mbox.setText(textHeader);
		mbox.setMessage(message);
		return mbox.open();
	}

}
