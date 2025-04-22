		private boolean anyFile(IResource[] members) {
			for (IResource member : members) {
				if (member.getType() == IResource.FILE) {
					return true;
				} else if (isContainer(member) && containsFiles(member)) {
					return true;
				}
			}
			return false;
		}
	}
