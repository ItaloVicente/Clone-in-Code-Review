			Collection<IPath> paths = getSelectedFileAndFolderPaths(event);
			for (IPath path : paths) {
				String repoRelativepath;
				if (path.equals(workTreePath))
					repoRelativepath = "."; //$NON-NLS-1$
				else
					repoRelativepath = path
							.removeFirstSegments(
									path.matchingFirstSegments(workTreePath))
							.setDevice(null).toString();
				addCommand.addFilepattern(repoRelativepath);
			}
