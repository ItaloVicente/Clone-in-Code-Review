package org.eclipse.jgit.internal.storage.file;

class FileWindowReaderFactory {
	private static boolean mmap = false;

	static void setMmap(boolean useMmap) {
		mmap = useMmap;
	}

	static FileWindowReader create(Pack pack) {
		if (mmap) {
			return new MmapFileWindowReader(pack);
		}
		return new HeapFileWindowReader(pack);
	}
}
