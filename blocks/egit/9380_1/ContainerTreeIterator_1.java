				if (rsrc instanceof IFile || mode == FileMode.SYMLINK) {
					try {
						length = FS.DETECTED.length(asFile());
					} catch (IOException e) {
						length = 0;
					}
				} else
