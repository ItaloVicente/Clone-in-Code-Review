
package org.eclipse.jgit.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.FS.Attributes;

public class FileUtils {

	public static final int NONE = 0;

	public static final int RECURSIVE = 1;

	public static final int RETRY = 2;

	public static final int SKIP_MISSING = 4;

	public static final int IGNORE_ERRORS = 8;

	public static final int EMPTY_DIRECTORIES_ONLY = 16;

	public static Path toPath(File f) throws IOException {
		try {
			return f.toPath();
		} catch (InvalidPathException ex) {
			throw new IOException(ex);
		}
	}

	public static void delete(File f) throws IOException {
		delete(f
	}

	public static void delete(File f
		FS fs = FS.DETECTED;
		if ((options & SKIP_MISSING) != 0 && !fs.exists(f))
			return;

		if ((options & RECURSIVE) != 0 && fs.isDirectory(f)) {
			final File[] items = f.listFiles();
			if (items != null) {
				List<File> files = new ArrayList<>();
				List<File> dirs = new ArrayList<>();
				for (File c : items)
					if (c.isFile())
						files.add(c);
					else
						dirs.add(c);
				for (File file : files)
					delete(file
				for (File d : dirs)
					delete(d
			}
		}

		boolean delete = false;
		if ((options & EMPTY_DIRECTORIES_ONLY) != 0) {
			if (f.isDirectory()) {
				delete = true;
			} else if ((options & IGNORE_ERRORS) == 0) {
				throw new IOException(MessageFormat.format(
						JGitText.get().deleteFileFailed
			}
		} else {
			delete = true;
		}

		if (delete) {
			Throwable t = null;
			Path p = f.toPath();
			try {
				Files.delete(p);
				return;
			} catch (FileNotFoundException e) {
				if ((options & (SKIP_MISSING | IGNORE_ERRORS)) == 0) {
					throw new IOException(MessageFormat.format(
							JGitText.get().deleteFileFailed
							f.getAbsolutePath())
				}
				return;
			} catch (IOException e) {
				t = e;
			}
			if ((options & RETRY) != 0) {
				for (int i = 1; i < 10; i++) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
					}
					try {
						Files.deleteIfExists(p);
						return;
					} catch (IOException e) {
						t = e;
					}
				}
			}
			if ((options & IGNORE_ERRORS) == 0) {
				throw new IOException(MessageFormat.format(
						JGitText.get().deleteFileFailed
						t);
			}
		}
	}

	public static void rename(File src
			throws IOException {
		rename(src
	}

	public static void rename(final File src
			CopyOption... options)
					throws AtomicMoveNotSupportedException
		int attempts = FS.DETECTED.retryFailedLockFileCommit() ? 10 : 1;
		while (--attempts >= 0) {
			try {
				Files.move(toPath(src)
				return;
			} catch (AtomicMoveNotSupportedException e) {
				throw e;
			} catch (IOException e) {
				try {
					if (!dst.delete()) {
						delete(dst
					}
					Files.move(toPath(src)
					return;
				} catch (IOException e2) {
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new IOException(
						MessageFormat.format(JGitText.get().renameFileFailed
								src.getAbsolutePath()
			}
		}
		throw new IOException(
				MessageFormat.format(JGitText.get().renameFileFailed
						src.getAbsolutePath()
	}

	public static void mkdir(File d)
			throws IOException {
		mkdir(d
	}

	public static void mkdir(File d
			throws IOException {
		if (!d.mkdir()) {
			if (skipExisting && d.isDirectory())
				return;
			throw new IOException(MessageFormat.format(
					JGitText.get().mkDirFailed
		}
	}

	public static void mkdirs(File d) throws IOException {
		mkdirs(d
	}

	public static void mkdirs(File d
			throws IOException {
		if (!d.mkdirs()) {
			if (skipExisting && d.isDirectory())
				return;
			throw new IOException(MessageFormat.format(
					JGitText.get().mkDirsFailed
		}
	}

	public static void createNewFile(File f) throws IOException {
		if (!f.createNewFile())
			throw new IOException(MessageFormat.format(
					JGitText.get().createNewFileFailed
	}

	public static Path createSymLink(File path
			throws IOException {
		Path nioPath = toPath(path);
		if (Files.exists(nioPath
			BasicFileAttributes attrs = Files.readAttributes(nioPath
					BasicFileAttributes.class
			if (attrs.isRegularFile() || attrs.isSymbolicLink()) {
				delete(path);
			} else {
				delete(path
			}
		}
		if (SystemReader.getInstance().isWindows()) {
			target = target.replace('/'
		}
		Path nioTarget = toPath(new File(target));
		return Files.createSymbolicLink(nioPath
	}

	public static String readSymLink(File path) throws IOException {
		Path nioPath = toPath(path);
		Path target = Files.readSymbolicLink(nioPath);
		String targetString = target.toString();
		if (SystemReader.getInstance().isWindows()) {
			targetString = targetString.replace('\\'
		} else if (SystemReader.getInstance().isMacOS()) {
			targetString = Normalizer.normalize(targetString
		}
		return targetString;
	}

	public static File createTempDir(String prefix
			throws IOException {
		for (int i = 0; i < RETRIES; i++) {
			File tmp = File.createTempFile(prefix
			if (!tmp.delete())
				continue;
			if (!tmp.mkdir())
				continue;
			return tmp;
		}
		throw new IOException(JGitText.get().cannotCreateTempDir);
	}

	public static String relativizeNativePath(String base
		return FS.DETECTED.relativize(base
	}

	public static String relativizeGitPath(String base
		return relativizePath(base
	}


	public static String relativizePath(String base
		if (base.equals(other))

		final String[] baseSegments = base.split(Pattern.quote(dirSeparator));
		final String[] otherSegments = other.split(Pattern
				.quote(dirSeparator));

		int commonPrefix = 0;
		while (commonPrefix < baseSegments.length
				&& commonPrefix < otherSegments.length) {
			if (caseSensitive
					&& baseSegments[commonPrefix]
					.equals(otherSegments[commonPrefix]))
				commonPrefix++;
			else if (!caseSensitive
					&& baseSegments[commonPrefix]
							.equalsIgnoreCase(otherSegments[commonPrefix]))
				commonPrefix++;
			else
				break;
		}

		final StringBuilder builder = new StringBuilder();
		for (int i = commonPrefix; i < baseSegments.length; i++)
		for (int i = commonPrefix; i < otherSegments.length; i++) {
			builder.append(otherSegments[i]);
			if (i < otherSegments.length - 1)
				builder.append(dirSeparator);
		}
		return builder.toString();
	}

	public static boolean isStaleFileHandle(IOException ioe) {
		String msg = ioe.getMessage();
		return msg != null
				&& msg.toLowerCase(Locale.ROOT)
	}

	public static boolean isStaleFileHandleInCausalChain(Throwable throwable) {
		while (throwable != null) {
			if (throwable instanceof IOException
					&& isStaleFileHandle((IOException) throwable)) {
				return true;
			}
			throwable = throwable.getCause();
		}
		return false;
	}

	static boolean isSymlink(File file) {
		return Files.isSymbolicLink(file.toPath());
	}

	static long lastModified(File file) throws IOException {
		return Files.getLastModifiedTime(toPath(file)
				.toMillis();
	}

	static BasicFileAttributes fileAttributes(File file) throws IOException {
		return Files.readAttributes(file.toPath()
	}

	static void setLastModified(File file
		Files.setLastModifiedTime(toPath(file)
	}

	static boolean exists(File file) {
		return Files.exists(file.toPath()
	}

	static boolean isHidden(File file) throws IOException {
		return Files.isHidden(toPath(file));
	}

	public static void setHidden(File file
		Files.setAttribute(toPath(file)
				LinkOption.NOFOLLOW_LINKS);
	}

	public static long getLength(File file) throws IOException {
		Path nioPath = toPath(file);
		if (Files.isSymbolicLink(nioPath))
			return Files.readSymbolicLink(nioPath).toString()
					.getBytes(UTF_8).length;
		return Files.size(nioPath);
	}

	static boolean isDirectory(File file) {
		return Files.isDirectory(file.toPath()
	}

	static boolean isFile(File file) {
		return Files.isRegularFile(file.toPath()
	}

	public static boolean canExecute(File file) {
		if (!isFile(file)) {
			return false;
		}
		return Files.isExecutable(file.toPath());
	}

	static Attributes getFileAttributesBasic(FS fs
		try {
			Path nioPath = toPath(file);
			BasicFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath
							BasicFileAttributeView.class
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new Attributes(fs
					true
					readAttributes.isDirectory()
					fs.supportsExecute() ? file.canExecute() : false
					readAttributes.isSymbolicLink()
					readAttributes.isRegularFile()
					readAttributes.creationTime().toMillis()
					readAttributes.lastModifiedTime().toMillis()
					readAttributes.isSymbolicLink() ? Constants
							.encode(readSymLink(file)).length
							: readAttributes.size());
			return attributes;
		} catch (IOException e) {
			return new Attributes(file
		}
	}

	public static Attributes getFileAttributesPosix(FS fs
		try {
			Path nioPath = toPath(file);
			PosixFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath
							PosixFileAttributeView.class
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new Attributes(
					fs
					file
					true
					readAttributes.isDirectory()
					readAttributes.permissions().contains(
							PosixFilePermission.OWNER_EXECUTE)
					readAttributes.isSymbolicLink()
					readAttributes.isRegularFile()
					readAttributes.creationTime().toMillis()
					readAttributes.lastModifiedTime().toMillis()
					readAttributes.size());
			return attributes;
		} catch (IOException e) {
			return new Attributes(file
		}
	}

	public static File normalize(File file) {
		if (SystemReader.getInstance().isMacOS()) {
			String normalized = Normalizer.normalize(file.getPath()
					Normalizer.Form.NFC);
			return new File(normalized);
		}
		return file;
	}

	public static String normalize(String name) {
		if (SystemReader.getInstance().isMacOS()) {
			if (name == null)
				return null;
			return Normalizer.normalize(name
		}
		return name;
	}

	public static File canonicalize(File file) {
		if (file == null) {
			return null;
		}
		try {
			return file.getCanonicalFile();
		} catch (IOException e) {
			return file;
		}
	}

	public static String pathToString(File file) {
		final String path = file.getPath();
		if (SystemReader.getInstance().isWindows()) {
			return path.replace('\\'
		}
		return path;
	}
}
