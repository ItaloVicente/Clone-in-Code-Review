				if (rsrc instanceof IFile || mode == FileMode.SYMLINK) {
					try {
						File file = asFile();
						if (file != null)
							length = FS.DETECTED.length(asFile());
						else
							length = 0;
					} catch (IOException e) {
