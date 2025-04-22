	 * Rename a file or folder using the passed {@link CopyOption}s. If the
	 * rename fails and if we are running on a filesystem where it makes sense
	 * to repeat a failing rename then repeat the rename operation up to 9 times
	 * with 100ms sleep time between two calls. Furthermore if the destination
	 * exists and is a directory hierarchy with only directories in it, the
	 * whole directory hierarchy will be deleted. If the target represents a
	 * non-empty directory structure, empty subdirectories within that structure
	 * may or may not be deleted even if the method fails. Furthermore if the
	 * destination exists and is a file then the file will be replaced if
	 * {@link StandardCopyOption#REPLACE_EXISTING} has been set. If
	 * {@link StandardCopyOption#ATOMIC_MOVE} has been set the rename will be
	 * done atomically or fail with an {@link AtomicMoveNotSupportedException}
