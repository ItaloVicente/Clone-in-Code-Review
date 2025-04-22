package org.eclipse.jgit.api;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class ArchiveCommand extends GitCommand<OutputStream> {
	public static interface Format<T extends Closeable> {
		T createArchiveOutputStream(OutputStream s) throws IOException;

		T createArchiveOutputStream(OutputStream s
				throws IOException;

		void putEntry(T out
				ObjectLoader loader) throws IOException;

		Iterable<String> suffixes();
	}

	public static class UnsupportedFormatException extends GitAPIException {
		private static final long serialVersionUID = 1L;

		private final String format;

		public UnsupportedFormatException(String format) {
			super(MessageFormat.format(JGitText.get().unsupportedArchiveFormat
			this.format = format;
		}

		public String getFormat() {
			return format;
		}
	}

	private static class FormatEntry {
		final Format<?> format;
		final int refcnt;

		public FormatEntry(Format<?> format
			if (format == null)
				throw new NullPointerException();
			this.format = format;
			this.refcnt = refcnt;
		}
	}

	private static final ConcurrentMap<String
			new ConcurrentHashMap<>();

	private static <K
			K key
			return true;

		if (oldValue == null)
			return map.putIfAbsent(key
		else if (newValue == null)
			return map.remove(key
		else
			return map.replace(key
	}

	public static void registerFormat(String name
		if (fmt == null)
			throw new NullPointerException();

		FormatEntry old
		do {
			old = formats.get(name);
			if (old == null) {
				entry = new FormatEntry(fmt
				continue;
			}
			if (!old.format.equals(fmt))
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().archiveFormatAlreadyRegistered
						name));
			entry = new FormatEntry(old.format
		} while (!replace(formats
	}

	public static void unregisterFormat(String name) {
		FormatEntry old
		do {
			old = formats.get(name);
			if (old == null)
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().archiveFormatAlreadyAbsent
						name));
			if (old.refcnt == 1) {
				entry = null;
				continue;
			}
			entry = new FormatEntry(old.format
		} while (!replace(formats
	}

	private static Format<?> formatBySuffix(String filenameSuffix)
			throws UnsupportedFormatException {
		if (filenameSuffix != null)
			for (FormatEntry entry : formats.values()) {
				Format<?> fmt = entry.format;
				for (String sfx : fmt.suffixes())
					if (filenameSuffix.endsWith(sfx))
						return fmt;
			}
	}

	private static Format<?> lookupFormat(String formatName) throws UnsupportedFormatException {
		FormatEntry entry = formats.get(formatName);
		if (entry == null)
			throw new UnsupportedFormatException(formatName);
		return entry.format;
	}

	private OutputStream out;
	private ObjectId tree;
	private String prefix;
	private String format;
	private Map<String
	private List<String> paths = new ArrayList<>();

	private String suffix;

	public ArchiveCommand(Repository repo) {
		super(repo);
		setCallable(false);
	}

	private <T extends Closeable> OutputStream writeArchive(Format<T> fmt) {
		try {
			try (TreeWalk walk = new TreeWalk(repo);
					RevWalk rw = new RevWalk(walk.getObjectReader());
					T outa = fmt.createArchiveOutputStream(out
							formatOptions)) {
				MutableObjectId idBuf = new MutableObjectId();
				ObjectReader reader = walk.getObjectReader();

				walk.reset(rw.parseTree(tree));
				if (!paths.isEmpty())
					walk.setFilter(PathFilterGroup.createFromStrings(paths));

					fmt.putEntry(outa
							FileMode.TREE
				}

				while (walk.next()) {
					String name = pfx + walk.getPathString();
					FileMode mode = walk.getFileMode(0);

					if (walk.isSubtree())
						walk.enterSubtree();

					if (mode == FileMode.GITLINK)
						mode = FileMode.TREE;

					if (mode == FileMode.TREE) {
						fmt.putEntry(outa
						continue;
					}
					walk.getObjectId(idBuf
					fmt.putEntry(outa
				}
				return out;
			} finally {
				out.close();
			}
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfArchiveCommand
		}
	}

	@Override
	public OutputStream call() throws GitAPIException {
		checkCallable();

		Format<?> fmt;
		if (format == null)
			fmt = formatBySuffix(suffix);
		else
			fmt = lookupFormat(format);
		return writeArchive(fmt);
	}

	public ArchiveCommand setTree(ObjectId tree) {
		if (tree == null)
			throw new IllegalArgumentException();

		this.tree = tree;
		setCallable(true);
		return this;
	}

	public ArchiveCommand setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public ArchiveCommand setFilename(String filename) {
		int slash = filename.lastIndexOf('/');
		int dot = filename.indexOf('.'

		if (dot == -1)
		else
			this.suffix = filename.substring(dot);
		return this;
	}

	public ArchiveCommand setOutputStream(OutputStream out) {
		this.out = out;
		return this;
	}

	public ArchiveCommand setFormat(String fmt) {
		this.format = fmt;
		return this;
	}

	public ArchiveCommand setFormatOptions(Map<String
		this.formatOptions = options;
		return this;
	}

	public ArchiveCommand setPaths(String... paths) {
		this.paths = Arrays.asList(paths);
		return this;
	}
}
