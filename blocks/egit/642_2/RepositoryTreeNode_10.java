	public int compareTo(RepositoryTreeNode otherNode) {

		int typeDiff = otherNode.getType().ordinal() - this.myType.ordinal();
		if (typeDiff != 0)
			return typeDiff;


		switch (myType) {

		case BRANCHES:
		case PROJECTS:
		case REMOTES:
		case WORKINGDIR:
			return 0;

		case FETCH:
		case PROJ:
		case PUSH:
		case REMOTE:
			return ((String) myObject)
					.compareTo((String) otherNode.getObject());
		case FILE:
		case FOLDER:
			return ((File) myObject).getName().compareTo(
					((File) otherNode.getObject()).getName());
		case REF:
			return ((Ref) myObject).getName().compareTo(
					((Ref) otherNode.getObject()).getName());
		case REPO:
			int nameCompare = ((Repository) myObject).getDirectory()
					.getParentFile().getName().compareTo(
							(((Repository) otherNode.getObject())
									.getDirectory().getParentFile().getName()));
			if (nameCompare != 0)
				return nameCompare;
			return ((Repository) myObject).getDirectory().getParentFile()
					.getParentFile().getPath().compareTo(
							(((Repository) otherNode.getObject())
									.getDirectory().getParentFile()
									.getParentFile().getPath()));

		}
		return 0;
	}

