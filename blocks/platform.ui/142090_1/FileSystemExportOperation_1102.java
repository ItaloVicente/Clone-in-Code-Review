			try {
				exportChildren(((IContainer) resource).members(), path);
			} catch (CoreException e) {
				errorTable.add(e.getStatus());
			}
		}
	}

	protected void exportChildren(IResource[] children, IPath currentPath)
			throws InterruptedException {
