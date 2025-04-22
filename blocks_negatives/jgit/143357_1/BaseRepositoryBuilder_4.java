		if (root != null) {
			if (ceilingDirectories == null)
				ceilingDirectories = new LinkedList<>();
			ceilingDirectories.add(root);
		}
		return self();
