
	public List<String> getUntrackedFolders() {
		LinkedList<String> ret = new LinkedList<String>(untrackedFolders);
		if (!untrackedParentFolders.isEmpty()) {
			String toBeAdded = untrackedParentFolders.getLast();
			while (!ret.isEmpty() && ret.getLast().startsWith(toBeAdded))
				ret.removeLast();
			ret.addLast(toBeAdded);
		}
		return ret;
	}
