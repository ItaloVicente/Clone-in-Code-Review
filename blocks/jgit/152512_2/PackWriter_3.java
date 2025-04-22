package org.eclipse.jgit.internal.storage.pack;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BitmapIndex.Bitmap;
import org.eclipse.jgit.lib.ObjectId;

public final class BitmapCommit extends ObjectId {
	private final boolean reuseWalker;

	private final int flags;

	private final Bitmap bitmap;

	private final boolean addToIndex;

	BitmapCommit(AnyObjectId objectId
		super(objectId);
		this.reuseWalker = reuseWalker;
		this.flags = flags;
		this.bitmap = null;
		this.addToIndex = false;
	}

	private BitmapCommit(AnyObjectId objectId
			Bitmap bitmap
		super(objectId);
		this.reuseWalker = reuseWalker;
		this.flags = flags;
		this.bitmap = bitmap;
		this.addToIndex = addToIndex;
	}

	boolean isReuseWalker() {
		return reuseWalker;
	}

	int getFlags() {
		return flags;
	}

	public boolean isAddToIndex() {
		return addToIndex;
	}

	public static Builder newBuilder(AnyObjectId objId) {
		return new Builder().setId(objId);
	}

	public static Builder copyFrom(BitmapCommit cmit) {
		return new Builder().setId(cmit).setReuseWalker(cmit.isReuseWalker())
				.setFlags(cmit.getFlags()).setBitmap(cmit.getBitmap())
				.setAddToIndex(cmit.isAddToIndex());
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public static class Builder {
		private AnyObjectId objId;

		private boolean reuseWalker;

		private int flags;

		private Bitmap bitmap;

		private boolean addToIndex;

		private Builder() {
		}

		public Builder setId(AnyObjectId objId) {
			this.objId = objId;
			return this;
		}

		public Builder setReuseWalker(boolean reuseWalker) {
			this.reuseWalker = reuseWalker;
			return this;
		}

		public Builder setFlags(int flags) {
			this.flags = flags;
			return this;
		}

		public Builder setBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
			return this;
		}

		public Builder setAddToIndex(boolean addToIndex) {
			this.addToIndex = addToIndex;
			return this;
		}

		public BitmapCommit build() {
			return new BitmapCommit(objId
					addToIndex);
		}
	}
}
