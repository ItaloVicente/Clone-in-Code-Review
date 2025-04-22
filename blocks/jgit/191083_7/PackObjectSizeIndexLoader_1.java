package org.eclipse.jgit.internal.storage.file;

public interface PackObjectSizeIndex {

	long getSize(long offset);

	long getObjectCount();
}
