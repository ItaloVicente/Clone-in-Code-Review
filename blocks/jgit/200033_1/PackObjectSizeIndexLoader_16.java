package org.eclipse.jgit.internal.storage.file;

public interface PackObjectSizeIndex {

	long getSize(int idxOffset);

	long getObjectCount();


	int getThreshold();
}
