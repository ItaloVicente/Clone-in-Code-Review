
package org.eclipse.jgit.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import org.eclipse.jgit.errors.CommandFailedException;
import org.eclipse.jgit.treewalk.FileTreeIterator.FileEntry;
import org.eclipse.jgit.treewalk.FileTreeIterator.FileModeStrategy;
import org.eclipse.jgit.treewalk.WorkingTreeIterator.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FS_Win32 extends FS {
	private final static Logger LOG = LoggerFactory.getLogger(FS_Win32.class);

	private volatile Boolean supportSymlinks;

	public FS_Win32() {
		super();
	}

	protected FS_Win32(FS src) {
		super(src);
	}

	@Override
	public FS newInstance() {
		return new FS_Win32(this);
	}

	@Override
	public boolean supportsExecute() {
		return false;
	}

	@Override
	public boolean canExecute(File f) {
		return false;
	}

	@Override
	public boolean setExecute(File f
		return false;
	}

	@Override
	public boolean isCaseSensitive() {
		return false;
	}

	@Override
	public boolean retryFailedLockFileCommit() {
		return true;
	}

	@Override
	public Entry[] list(File directory
		List<Entry> result = new ArrayList<>();
		FS fs = this;
		boolean checkExecutable = fs.supportsExecute();
		try {
			Files.walkFileTree(directory.toPath()
					EnumSet.noneOf(FileVisitOption.class)
					new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult visitFile(Path file
								BasicFileAttributes attrs) throws IOException {
							File f = file.toFile();
							FS.Attributes attributes = new FS.Attributes(fs
									true
									checkExecutable && f.canExecute()
									attrs.isSymbolicLink()
									attrs.isRegularFile()
									attrs.creationTime().toMillis()
									attrs.lastModifiedTime().toMillis()
									attrs.size());
							result.add(new FileEntry(f
									fileModeStrategy));
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFileFailed(Path file
								IOException exc) throws IOException {
							return FileVisitResult.CONTINUE;
						}
					});
		} catch (IOException e) {
		}
		if (result.isEmpty()) {
			return NO_ENTRIES;
		}
		return result.toArray(new Entry[0]);
	}

	@Override
	protected File discoverGitExe() {
		File gitExe = searchPath(path

		if (gitExe == null) {
			if (searchPath(path
				String w;
				try {
					w = readPipe(userHome()
						new String[]{"bash"
						Charset.defaultCharset().name());
				} catch (CommandFailedException e) {
					LOG.warn(e.getMessage());
					return null;
				}
				if (!StringUtils.isEmptyOrNull(w)) {
					gitExe = resolve(null
				}
			}
		}

		return gitExe;
	}

	@Override
	protected File userHomeImpl() {
		if (home != null)
			return resolve(null
		if (homeDrive != null) {
			if (homePath != null)
				return new File(homeDrive
		}

		if (homeShare != null)
			return new File(homeShare);

		return super.userHomeImpl();
	}

	@Override
	public ProcessBuilder runInShell(String cmd
		List<String> argv = new ArrayList<>(3 + args.length);
		argv.add(cmd);
		argv.addAll(Arrays.asList(args));
		ProcessBuilder proc = new ProcessBuilder();
		proc.command(argv);
		return proc;
	}

	@Override
	public boolean supportsSymlinks() {
		if (supportSymlinks == null)
			detectSymlinkSupport();
		return Boolean.TRUE.equals(supportSymlinks);
	}

	private void detectSymlinkSupport() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("tempsymlinktarget"
			File linkName = new File(tempFile.getParentFile()
			createSymLink(linkName
			supportSymlinks = Boolean.TRUE;
			linkName.delete();
		} catch (IOException | UnsupportedOperationException
				| InternalError e) {
			supportSymlinks = Boolean.FALSE;
		} finally {
			if (tempFile != null)
				try {
					FileUtils.delete(tempFile);
				} catch (IOException e) {
				}
		}
	}

	@Override
	public Attributes getAttributes(File path) {
		return FileUtils.getFileAttributesBasic(this
	}
}
