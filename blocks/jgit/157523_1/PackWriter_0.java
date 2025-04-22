package org.eclipse.jgit.internal.storage.pack;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

public final class BitmapCommit extends ObjectId {

	private final boolean reuseWalker;

	private final int flags;

	private final boolean addToIndex;

	BitmapCommit(AnyObjectId objectId
		super(objectId);
		this.reuseWalker = reuseWalker;
		this.flags = flags;
		this.addToIndex = false;
	}

	BitmapCommit(AnyObjectId objectId
			boolean addToIndex) {
		super(objectId);
		this.reuseWalker = reuseWalker;
		this.flags = flags;
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

	public static Builder copyFrom(BitmapCommit commit) {
		return new Builder().setId(commit)
				.setReuseWalker(commit.isReuseWalker())
				.setFlags(commit.getFlags())
				.setAddToIndex(commit.isAddToIndex());
	}

	public static class Builder {
		private AnyObjectId objectId;

		private boolean reuseWalker;

		private int flags;

		private boolean addToIndex;

		private Builder() {
		}

		public Builder setId(AnyObjectId objectId) {
			this.objectId = objectId;
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

		public Builder setAddToIndex(boolean addToIndex) {
			this.addToIndex = addToIndex;
			return this;
		}

		public BitmapCommit build() {
			return new BitmapCommit(objectId
					addToIndex);
		}
	}
}
