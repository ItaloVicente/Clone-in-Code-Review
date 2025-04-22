	 * Frees resources held by the underlying {@link Repository} instance. It is
	 * recommended to call this method as soon as you don't need a reference to
	 * this {@link Git} instance and the underlying {@link Repository} instance
	 * anymore. This method closes the underlying object and ref databases. This
	 * will free memory and file handles. E.g. on Windows the repository will
	 * keep file handles on pack files unless you call this method. Such open
	 * file handles may for example prevent that the repository folder in the
	 * filesystem can be deleted.
