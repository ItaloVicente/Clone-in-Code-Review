
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
					IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}

