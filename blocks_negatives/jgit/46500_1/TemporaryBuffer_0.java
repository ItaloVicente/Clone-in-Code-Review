		/**
		 * Create a new temporary buffer.
		 *
		 * @deprecated Use the {@code File} overload to supply a directory.
		 */
		@Deprecated
		public LocalFile() {
			this(null, DEFAULT_IN_CORE_LIMIT);
		}

		/**
		 * Create a new temporary buffer, limiting memory usage.
		 *
		 * @param inCoreLimit
		 *            maximum number of bytes to store in memory. Storage beyond
		 *            this limit will use the local file.
		 * @deprecated Use the {@code File,int} overload to supply a directory.
		 */
		@Deprecated
		public LocalFile(final int inCoreLimit) {
			this(null, inCoreLimit);
		}

