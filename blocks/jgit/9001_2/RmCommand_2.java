				if (!cached) {
					final FileMode mode = tw.getFileMode(0);
					if (mode.getObjectType() == Constants.OBJ_BLOB) {
						final File path = new File(repo.getWorkTree()
								tw.getPathString());
						delete(path);
					}
