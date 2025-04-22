				if (FS.DETECTED.supportsExecute() && file != null
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
