
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

public abstract class ObjectReader implements AutoCloseable {
	public static final int OBJ_ANY = -1;

	protected int streamFileThreshold;

	public abstract ObjectReader newReader();

	public AbbreviatedObjectId abbreviate(AnyObjectId objectId)
			throws IOException {
		return abbreviate(objectId
	}

	public AbbreviatedObjectId abbreviate(AnyObjectId objectId
			throws IOException {
		if (len == Constants.OBJECT_ID_STRING_LENGTH)
			return AbbreviatedObjectId.fromObjectId(objectId);

		AbbreviatedObjectId abbrev = objectId.abbreviate(len);
		Collection<ObjectId> matches = resolve(abbrev);
		while (1 < matches.size() && len < Constants.OBJECT_ID_STRING_LENGTH) {
			abbrev = objectId.abbreviate(++len);
			List<ObjectId> n = new ArrayList<>(8);
			for (ObjectId candidate : matches) {
				if (abbrev.prefixCompare(candidate) == 0)
					n.add(candidate);
			}
			if (1 < n.size())
				matches = n;
			else
				matches = resolve(abbrev);
		}
		return abbrev;
	}

	public abstract Collection<ObjectId> resolve(AbbreviatedObjectId id)
			throws IOException;

	public boolean has(AnyObjectId objectId) throws IOException {
		return has(objectId
	}

	public boolean has(AnyObjectId objectId
		try {
			open(objectId
			return true;
		} catch (MissingObjectException notFound) {
			return false;
		}
	}

	public ObjectLoader open(AnyObjectId objectId)
			throws MissingObjectException
		return open(objectId
	}

	public abstract ObjectLoader open(AnyObjectId objectId
			throws MissingObjectException
			IOException;

	public abstract Set<ObjectId> getShallowCommits() throws IOException;

	public <T extends ObjectId> AsyncObjectLoaderQueue<T> open(
			Iterable<T> objectIds
		final Iterator<T> idItr = objectIds.iterator();
		return new AsyncObjectLoaderQueue<T>() {
			private T cur;

			@Override
			public boolean next() throws MissingObjectException
				if (idItr.hasNext()) {
					cur = idItr.next();
					return true;
				} else {
					return false;
				}
			}

			@Override
			public T getCurrent() {
				return cur;
			}

			@Override
			public ObjectId getObjectId() {
				return cur;
			}

			@Override
			public ObjectLoader open() throws IOException {
				return ObjectReader.this.open(cur
			}

			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return true;
			}

			@Override
			public void release() {
			}
		};
	}

	public long getObjectSize(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		return open(objectId
	}

	public <T extends ObjectId> AsyncObjectSizeQueue<T> getObjectSize(
			Iterable<T> objectIds
		final Iterator<T> idItr = objectIds.iterator();
		return new AsyncObjectSizeQueue<T>() {
			private T cur;

			private long sz;

			@Override
			public boolean next() throws MissingObjectException
				if (idItr.hasNext()) {
					cur = idItr.next();
					sz = getObjectSize(cur
					return true;
				} else {
					return false;
				}
			}

			@Override
			public T getCurrent() {
				return cur;
			}

			@Override
			public ObjectId getObjectId() {
				return cur;
			}

			@Override
			public long getSize() {
				return sz;
			}

			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return true;
			}

			@Override
			public void release() {
			}
		};
	}

	public void setAvoidUnreachableObjects(boolean avoid) {
	}

	public BitmapIndex getBitmapIndex() throws IOException {
		return null;
	}

	@Nullable
	public ObjectInserter getCreatedFromInserter() {
		return null;
	}

	@Override
	public abstract void close();

	public void setStreamFileThreshold(int threshold) {
		streamFileThreshold = threshold;
	}

	public int getStreamFileThreshold() {
		return streamFileThreshold;
	}

	public static abstract class Filter extends ObjectReader {
		protected abstract ObjectReader delegate();

		@Override
		public ObjectReader newReader() {
			return delegate().newReader();
		}

		@Override
		public AbbreviatedObjectId abbreviate(AnyObjectId objectId)
				throws IOException {
			return delegate().abbreviate(objectId);
		}

		@Override
		public AbbreviatedObjectId abbreviate(AnyObjectId objectId
				throws IOException {
			return delegate().abbreviate(objectId
		}

		@Override
		public Collection<ObjectId> resolve(AbbreviatedObjectId id)
				throws IOException {
			return delegate().resolve(id);
		}

		@Override
		public boolean has(AnyObjectId objectId) throws IOException {
			return delegate().has(objectId);
		}

		@Override
		public boolean has(AnyObjectId objectId
			return delegate().has(objectId
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId)
				throws MissingObjectException
			return delegate().open(objectId);
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId
				throws MissingObjectException
				IOException {
			return delegate().open(objectId
		}

		@Override
		public Set<ObjectId> getShallowCommits() throws IOException {
			return delegate().getShallowCommits();
		}

		@Override
		public <T extends ObjectId> AsyncObjectLoaderQueue<T> open(
				Iterable<T> objectIds
			return delegate().open(objectIds
		}

		@Override
		public long getObjectSize(AnyObjectId objectId
				throws MissingObjectException
				IOException {
			return delegate().getObjectSize(objectId
		}

		@Override
		public <T extends ObjectId> AsyncObjectSizeQueue<T> getObjectSize(
				Iterable<T> objectIds
			return delegate().getObjectSize(objectIds
		}

		@Override
		public void setAvoidUnreachableObjects(boolean avoid) {
			delegate().setAvoidUnreachableObjects(avoid);
		}

		@Override
		public BitmapIndex getBitmapIndex() throws IOException {
			return delegate().getBitmapIndex();
		}

		@Override
		@Nullable
		public ObjectInserter getCreatedFromInserter() {
			return delegate().getCreatedFromInserter();
		}

		@Override
		public void close() {
			delegate().close();
		}
	}
}
