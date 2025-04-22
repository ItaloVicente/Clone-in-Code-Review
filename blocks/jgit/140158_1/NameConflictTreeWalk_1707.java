
package org.eclipse.jgit.treewalk;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;

public class FileTreeIterator extends WorkingTreeIterator {

	protected final File directory;

	protected final FS fs;

	protected final FileModeStrategy fileModeStrategy;

	public FileTreeIterator(Repository repo) {
		this(repo
				repo.getConfig().get(WorkingTreeOptions.KEY).isDirNoGitLinks() ?
						NoGitlinksStrategy.INSTANCE :
						DefaultFileModeStrategy.INSTANCE);
	}

	public FileTreeIterator(Repository repo
		this(repo.getWorkTree()
				repo.getConfig().get(WorkingTreeOptions.KEY)
				fileModeStrategy);
		initRootIterator(repo);
	}

	public FileTreeIterator(File root
		this(root
	}

	public FileTreeIterator(final File root
							FileModeStrategy fileModeStrategy) {
		super(options);
		directory = root;
		this.fs = fs;
		this.fileModeStrategy = fileModeStrategy;
		init(entries());
	}

	protected FileTreeIterator(final FileTreeIterator p
			FS fs) {
		this(p
	}

	protected FileTreeIterator(final WorkingTreeIterator p
			FS fs
		super(p);
		directory = root;
		this.fs = fs;
		this.fileModeStrategy = fileModeStrategy;
		init(entries());
	}

	@Override
	public AbstractTreeIterator createSubtreeIterator(ObjectReader reader)
			throws IncorrectObjectTypeException
		if (!walksIgnoredDirectories() && isEntryIgnored()) {
			DirCacheIterator iterator = getDirCacheIterator();
			if (iterator == null) {
				return new EmptyTreeIterator(this);
			}
		}
		return enterSubtree();
	}


	protected AbstractTreeIterator enterSubtree() {
		return new FileTreeIterator(this
				fileModeStrategy);
	}

	private Entry[] entries() {
		return fs.list(directory
	}

	public interface FileModeStrategy {
		FileMode getMode(File f
	}

	public static class DefaultFileModeStrategy implements FileModeStrategy {
		public final static DefaultFileModeStrategy INSTANCE =
				new DefaultFileModeStrategy();

		@Override
		public FileMode getMode(File f
			if (attributes.isSymbolicLink()) {
				return FileMode.SYMLINK;
			} else if (attributes.isDirectory()) {
				if (new File(f
					return FileMode.GITLINK;
				} else {
					return FileMode.TREE;
				}
			} else if (attributes.isExecutable()) {
				return FileMode.EXECUTABLE_FILE;
			} else {
				return FileMode.REGULAR_FILE;
			}
		}
	}

	public static class NoGitlinksStrategy implements FileModeStrategy {

		public final static NoGitlinksStrategy INSTANCE = new NoGitlinksStrategy();

		@Override
		public FileMode getMode(File f
			if (attributes.isSymbolicLink()) {
				return FileMode.SYMLINK;
			} else if (attributes.isDirectory()) {
				return FileMode.TREE;
			} else if (attributes.isExecutable()) {
				return FileMode.EXECUTABLE_FILE;
			} else {
				return FileMode.REGULAR_FILE;
			}
		}
	}


	public static class FileEntry extends Entry {
		private final FileMode mode;

		private FS.Attributes attributes;

		private FS fs;

		public FileEntry(File f
			this(f
		}

		public FileEntry(File f
			this.fs = fs;
			f = fs.normalize(f);
			attributes = fs.getAttributes(f);
			mode = fileModeStrategy.getMode(f
		}

		public FileEntry(File f
				FileModeStrategy fileModeStrategy) {
			this.fs = fs;
			this.attributes = attributes;
			f = fs.normalize(f);
			mode = fileModeStrategy.getMode(f
		}

		@Override
		public FileMode getMode() {
			return mode;
		}

		@Override
		public String getName() {
			return attributes.getName();
		}

		@Override
		public long getLength() {
			return attributes.getLength();
		}

		@Override
		public long getLastModified() {
			return attributes.getLastModifiedTime();
		}

		@Override
		public InputStream openInputStream() throws IOException {
			if (attributes.isSymbolicLink()) {
				return new ByteArrayInputStream(fs.readSymLink(getFile())
						.getBytes(UTF_8));
			} else {
				return new FileInputStream(getFile());
			}
		}

		public File getFile() {
			return attributes.getFile();
		}
	}

	public File getDirectory() {
		return directory;
	}

	public File getEntryFile() {
		return ((FileEntry) current()).getFile();
	}

	@Override
	protected byte[] idSubmodule(Entry e) {
		return idSubmodule(getDirectory()
	}

	@Override
	protected String readSymlinkTarget(Entry entry) throws IOException {
		return fs.readSymLink(getEntryFile());
	}
}
