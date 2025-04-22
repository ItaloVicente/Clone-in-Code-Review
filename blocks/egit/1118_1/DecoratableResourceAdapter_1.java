
			long tIndex = indexEntry.getLastModified();
			if (resourceEntry.getResource().getType() == IResource.FILE &&
					tIndex / 1000000 == 2139062L) {
				InputStream inIndex;
				try {
					inIndex = GitFileRevision.inIndex(repository, indexEntry.getPathString())
							.getStorage(null).getContents();
					dirty = !compareContent(inIndex,
							((IFile)resourceEntry.getResource()).getContents());
				} catch (CoreException e) {
					IStatus error = new Status(IStatus.ERROR, Activator
							.getPluginId(), e.getMessage(), e);
					Activator.getDefault().getLog().log(error);
				}
			} else if (!timestampMatches(indexEntry, resourceEntry))
