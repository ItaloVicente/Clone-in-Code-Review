		final String myName = getRef().getLeaf().getName();
		if (myName.startsWith(Constants.R_HEADS)) {
			Ref head = getRefDatabase().getRef(Constants.HEAD);
			while (head.isSymbolic()) {
				head = head.getTarget();
				if (myName.equals(head.getName()))
					return result = Result.REJECTED_CURRENT_BRANCH;
			}
