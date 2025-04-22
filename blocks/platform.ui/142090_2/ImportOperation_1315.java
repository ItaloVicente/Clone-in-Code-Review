			else if (createLinks) {
				IFolder newFolder = workspace.getRoot().getFolder(resourcePath);
				newFolder.createLink(
						createRelativePath(new Path(provider.getFullPath(folderObject)), newFolder),
						0, null);
				policy = POLICY_SKIP_CHILDREN;
			} else
				workspace.getRoot().getFolder(resourcePath).create(false, true, null);
		} catch (CoreException e) {
			errorTable.add(e.getStatus());
		}

		return policy;
	}

	private IPath createRelativePath(IPath location, IResource resource) {
