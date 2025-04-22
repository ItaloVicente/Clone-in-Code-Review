		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String type = memento.getString(TAG_TYPE);
		if (type == null) {
			res = root.findMember(new Path(fileName));
		} else {
			int resourceType = Integer.parseInt(type);
