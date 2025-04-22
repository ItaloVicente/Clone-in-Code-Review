			FileMode mode = null;
			File file = asFile();
			try {
				if (FS.DETECTED.supportsSymlinks()
						&& FS.DETECTED.isSymLink(file))
					mode = FileMode.SYMLINK;
				else {
					switch (f.getType()) {
					case IResource.FILE:
						if (FS.DETECTED.supportsExecute()
									&& FS.DETECTED.canExecute(file))
								mode = FileMode.EXECUTABLE_FILE;
							else
								mode = FileMode.REGULAR_FILE;
						break;
					case IResource.PROJECT:
					case IResource.FOLDER: {
						final IContainer c = (IContainer) f;
						if (c.findMember(Constants.DOT_GIT) != null)
							mode = FileMode.GITLINK;
						else
							mode = FileMode.TREE;
						break;
					}
					default:
						mode = FileMode.MISSING;
						break;
					}
				}
			} catch (IOException e) {
