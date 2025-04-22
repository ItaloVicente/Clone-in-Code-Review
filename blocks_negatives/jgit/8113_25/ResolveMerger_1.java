	 * not set explicitly this merger will implicitly get and lock a default
	 * DirCache. If the DirCache is explicitly set the caller is responsible to
	 * lock it in advance. Finally the merger will call
	 * {@link DirCache#commit()} which requires that the DirCache is locked. If
	 * the {@link #mergeImpl()} returns without throwing an exception the lock
	 * will be released. In case of exceptions the caller is responsible to
	 * release the lock.
