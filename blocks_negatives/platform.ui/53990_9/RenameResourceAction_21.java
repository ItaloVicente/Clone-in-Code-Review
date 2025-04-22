		Runnable query = new Runnable() {
			@Override
			public void run() {
				try {
					if (!newName.equals(inlinedResource.getName())) {
						IWorkspace workspace = IDEWorkbenchPlugin
								.getPluginWorkspace();
						IStatus status = workspace.validateName(newName,
								inlinedResource.getType());
						if (!status.isOK()) {
							displayError(status.getMessage());
						} else {
							IPath newPath = inlinedResource.getFullPath()
									.removeLastSegments(1).append(newName);
							runWithNewPath(newPath, inlinedResource);
						}
