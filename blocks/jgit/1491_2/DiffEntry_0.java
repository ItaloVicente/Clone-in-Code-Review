
package org.eclipse.jgit.diff;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.PathFilter;

public abstract class ContentSource {
	public static ContentSource create(ObjectReader reader) {
		return new ObjectReaderSource(reader);
	}

	public static ContentSource create(WorkingTreeIterator iterator) {
		if (iterator instanceof FileTreeIterator) {
			FileTreeIterator i = (FileTreeIterator) iterator;
			return new FileSource(i.getDirectory());
		}
		return new WorkingTreeSource(iterator);
	}

	public abstract long size(String path

	public abstract ObjectLoader open(String path
			throws IOException;

	private static class ObjectReaderSource extends ContentSource {
		private final ObjectReader reader;

		ObjectReaderSource(ObjectReader reader) {
			this.reader = reader;
		}

		@Override
		public long size(String path
			return reader.getObjectSize(id
		}

		@Override
		public ObjectLoader open(String path
			return reader.open(id
		}
	}

	private static class WorkingTreeSource extends ContentSource {
		private final TreeWalk tw;

		private final WorkingTreeIterator iterator;

		private String current;

		private WorkingTreeIterator ptr;

		WorkingTreeSource(WorkingTreeIterator iterator) {
			this.tw = new TreeWalk((ObjectReader) null);
			this.iterator = iterator;
		}

		@Override
		public long size(String path
			seek(path);
			return ptr.getEntryLength();
		}

		@Override
		public ObjectLoader open(String path
			seek(path);
			return new ObjectLoader() {
				@Override
				public long getSize() {
					return ptr.getEntryLength();
				}

				@Override
				public int getType() {
					return ptr.getEntryFileMode().getObjectType();
				}

				@Override
				public ObjectStream openStream() throws MissingObjectException
						IOException {
					InputStream in = ptr.openEntryStream();
					in = new BufferedInputStream(in);
					return new ObjectStream.Filter(getType()
				}

				@Override
				public boolean isLarge() {
					return true;
				}

				@Override
				public byte[] getCachedBytes() throws LargeObjectException {
					throw new LargeObjectException();
				}
			};
		}

		private void seek(String path) throws IOException {
			if (!path.equals(current)) {
				iterator.reset();
				tw.reset();
				tw.addTree(iterator);
				tw.setFilter(PathFilter.create(path));
				current = path;
				if (!tw.next())
					throw new FileNotFoundException(path);
				ptr = tw.getTree(0
				if (ptr == null)
					throw new FileNotFoundException(path);
			}
		}
	}

	private static class FileSource extends ContentSource {
		private final File root;

		FileSource(File root) {
			this.root = root;
		}

		@Override
		public long size(String path
			return new File(root
		}

		@Override
		public ObjectLoader open(String path
			final File p = new File(root
			if (!p.isFile())
				throw new FileNotFoundException(path);
			return new ObjectLoader() {
				@Override
				public long getSize() {
					return p.length();
				}

				@Override
				public int getType() {
					return Constants.OBJ_BLOB;
				}

				@Override
				public ObjectStream openStream() throws MissingObjectException
						IOException {
					final FileInputStream in = new FileInputStream(p);
					final long sz = in.getChannel().size();
					final int type = getType();
					final BufferedInputStream b = new BufferedInputStream(in);
					return new ObjectStream.Filter(type
				}

				@Override
				public boolean isLarge() {
					return true;
				}

				@Override
				public byte[] getCachedBytes() throws LargeObjectException {
					throw new LargeObjectException();
				}
			};
		}
	}

	public static final class Pair {
		private final ContentSource oldSource;

		private final ContentSource newSource;

		public Pair(ContentSource oldSource
			this.oldSource = oldSource;
			this.newSource = newSource;
		}

		public long size(DiffEntry.Side side
			switch (side) {
			case OLD:
				return oldSource.size(ent.oldPath
			case NEW:
				return newSource.size(ent.newPath
			default:
				throw new IllegalArgumentException();
			}
		}

		public ObjectLoader open(DiffEntry.Side side
				throws IOException {
			switch (side) {
			case OLD:
				return oldSource.open(ent.oldPath
			case NEW:
				return newSource.open(ent.newPath
			default:
				throw new IllegalArgumentException();
			}
		}
	}
}
