		} else {
			if (isProjectInWorkspace) {
				fail(project.getName()
						+ " should not be in the workspace location: "
						+ rootLocation.toOSString());
			}
