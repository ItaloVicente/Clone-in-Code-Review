					changeRunnable = () -> {
						IPath path = delta.getMovedToPath();
						IFile newFile = delta.getResource().getWorkspace().getRoot().getFile(path);
						if (newFile != null) {
							sourceChanged(newFile);
						}
					};
