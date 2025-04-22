		if (children.length == 0)
			return new String[0];
		List<String> projects = new ArrayList<String>(children.length);
		for (int i = 0; i < children.length; i++) {
			String path = children[i].getTextData();
			if (path != null && path.length() > 0)
				projects.add(path);
		}
		return projects.toArray(new String[projects.size()]);
