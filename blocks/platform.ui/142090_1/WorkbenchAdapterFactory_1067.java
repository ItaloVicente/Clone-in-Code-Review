		if (o instanceof IResource) {
			switch (((IResource) o).getType()) {
			case IResource.FILE:
				return fileAdapter;
			case IResource.FOLDER:
				return folderAdapter;
			case IResource.PROJECT:
				return projectAdapter;
			}
		}
		if (o instanceof IWorkspaceRoot) {
			return rootAdapter;
		}
		if (o instanceof IWorkspace) {
			return workspaceAdapter;
		}
		if (o instanceof IMarker) {
			return markerAdapter;
		}
		return null;
	}

