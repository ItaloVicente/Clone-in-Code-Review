		/**
		 * Write an entry to an archive.
		 *
		 * @param out
		 *            archive object from createArchiveOutputStream
		 * @param path
		 *            full filename relative to the root of the archive (with
		 *            trailing '/' for directories)
		 * @param mode
		 *            mode (for example FileMode.REGULAR_FILE or
		 *            FileMode.SYMLINK)
		 * @param loader
		 *            blob object with data for this entry (null for
		 *            directories)
		 * @throws IOException
		 *             thrown by the underlying output stream for I/O errors
		 * @deprecated use
		 *             {@link #putEntry(Closeable, ObjectId, String, FileMode, ObjectLoader)}
		 *             instead
		 */
		@Deprecated
		void putEntry(T out, String path, FileMode mode,
					  ObjectLoader loader) throws IOException;

