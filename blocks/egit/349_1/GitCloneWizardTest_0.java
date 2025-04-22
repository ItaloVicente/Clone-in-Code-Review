		
		File workspace = new File(ResourcesPlugin.getWorkspace().getRoot()
				.getLocation().toFile().getParent(), "junit-workspace");
		if (workspace.exists()) {
			workspace.delete();
		}

