	private RepositoryTreeNodeType(String iconName) {

		if (iconName != null) {
			myImage = Activator.getDefault().getImageRegistry().get(
					iconName);
		} else {
			myImage = null;
		}

	}

