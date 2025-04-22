	private void getChildrenImpl() {
		List<GitModelObjectContainer> result = new ArrayList<GitModelObjectContainer>();
		if (srcRev != null && dstRev != null)
			result.addAll(getListOfCommit());
		else {
			GitModelWorkingTree changes = getLocaWorkingTreeChanges();
			if (changes != null)
				result.add(changes);
		}


		childrens = result.toArray(new GitModelObjectContainer[result.size()]);
	}

