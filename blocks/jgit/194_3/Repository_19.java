		Ref head = getRef(Constants.HEAD);
		if (head instanceof SymbolicRef)
			return ((SymbolicRef)head).getTarget().getName();
		if (head != null && head.getObjectId() != null)
			return head.getObjectId().name();
		return null;
