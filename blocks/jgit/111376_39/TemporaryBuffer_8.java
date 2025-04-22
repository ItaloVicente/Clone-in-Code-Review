package org.eclipse.jgit.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.MessageFormat;

import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.hooks.PrePushHook;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

public class LFS {

	private static LFS instance = new LFS();

	protected LFS() {
	}

	public static LFS getInstance() {
		return instance;
	}

	public static void setInstance(LFS instance) {
		LFS.instance = instance;
	}

	public boolean isAvailable() {
		return false;
	}

	public LfsInputStream getCleanFiltered(Repository db
			InputStream input
			throws IOException {
		return new LfsInputStream(input
	}

	public ObjectLoader getSmudgeFiltered(Repository db
			ObjectLoader loader
		return loader;
	}

	public PrePushHook getPrePushHook(Repository repo
			PrintStream outputStream) {
		return null;
	}

	public static Attributes getAttributesForPath(Repository db
			throws IOException {
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(new FileTreeIterator(db));
			PathFilter f = PathFilter.create(path);
			walk.setFilter(f);
			walk.setRecursive(false);
			Attributes attr = null;
			while (walk.next()) {
				if (f.isDone(walk)) {
					attr = walk.getAttributes();
					break;
				} else if (walk.isSubtree()) {
					walk.enterSubtree();
				}
			}
			if (attr == null) {
				throw new IOException(MessageFormat
						.format(JGitText.get().noPathAttributesFound
			}

			return attr;
		}
	}

	public static Attributes getAttributesForPath(Repository db
			RevCommit commit) throws IOException {
		if (commit == null) {
			return getAttributesForPath(db
		}

		try (TreeWalk walk = TreeWalk.forPath(db
			Attributes attr = walk == null ? null : walk.getAttributes();
			if (attr == null) {
				throw new IOException(MessageFormat
						.format(JGitText.get().noPathAttributesFound
			}

			return attr;
		}
	}

	public static final class LfsInputStream implements Closeable {
		public InputStream stream;

		public long length;

		public LfsInputStream(InputStream stream
			this.stream = stream;
			this.length = length;
		}

		public LfsInputStream(TemporaryBuffer buffer) throws IOException {
			this.stream = buffer.openInputStreamWithAutoDestroy();
			this.length = buffer.length();
		}

		@Override
		public void close() throws IOException {
			stream.close();
		}
	}

}
