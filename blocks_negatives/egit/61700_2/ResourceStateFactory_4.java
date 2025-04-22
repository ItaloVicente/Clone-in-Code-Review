	private boolean anyFile(IResource[] members) {
		for (IResource member : members) {
			if (member.getType() == IResource.FILE)
				return true;
			else if (member.getType() == IResource.FOLDER)
				if (hasContainerAnyFiles(member))
