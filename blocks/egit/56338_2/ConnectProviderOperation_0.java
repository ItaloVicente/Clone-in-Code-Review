
		if (refreshResources) {
			project.refreshLocal(IResource.DEPTH_INFINITE, subMon.newChild(40));
		} else {
			subMon.worked(40);
		}

		autoIgnoreDerivedResources(project, subMon);
