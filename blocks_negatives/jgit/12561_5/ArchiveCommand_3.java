			public void putEntry(String path, FileMode mode, //
					ObjectLoader loader, ArchiveOutputStream out) //
					throws IOException {
				if (mode == FileMode.SYMLINK) {
							path, TarConstants.LF_SYMLINK);
							loader.getCachedBytes(100), "UTF-8")); //$NON-NLS-1$
					out.putArchiveEntry(entry);
					out.closeArchiveEntry();
					return;
				}
