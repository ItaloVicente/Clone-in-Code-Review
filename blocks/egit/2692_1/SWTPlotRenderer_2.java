	private boolean isHead(String name) {
		boolean isHead = false;
		if (headRef != null) {
			String headRefName = headRef.getLeaf().getName();
			if (name.equals(headRefName)) {
				isHead = true;
			}
		}
		return isHead;
	}

