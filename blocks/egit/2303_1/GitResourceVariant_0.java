			if (isContainer()) {
				resource = workspaceRoot.getProject(location.lastSegment());
				if (resource == null)
					resource = workspaceRoot.getFolder(location);
			} else
				resource = workspaceRoot.getFile(location.makeRelativeTo(workspaceRoot.getLocation()));
