package org.eclipse.jgit.internal.storage.file;

public interface PackObjectSizeIndex {

	long getSize(int idxOffset);

	long getObjectCount();

	PackObjectSizeIndex EMPTY = new PackObjectSizeIndex() {
		@Override
		public long getSize(int idxOffset) {
			return -1;
		}

		@Override
		public long getObjectCount() {
			return 0;
		}
	};
}
