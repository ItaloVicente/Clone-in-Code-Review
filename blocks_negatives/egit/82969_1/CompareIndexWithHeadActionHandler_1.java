				IPath location = (IPath) (fileOrPath instanceof IPath ? fileOrPath
						: ((IResource) fileOrPath).getLocation());
				if (!isStaged(repository, location, true)) {
					showNoStagedFileInfo(location);
					return Status.CANCEL_STATUS;
				}
