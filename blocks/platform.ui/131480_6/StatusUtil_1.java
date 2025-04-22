		List flatStatusCollection = new ArrayList();
		Iterator iter = children.iterator();
		while (iter.hasNext()) {
			IStatus currentStatus = (IStatus) iter.next();
			Iterator childrenIter = flatten(currentStatus).iterator();
			while (childrenIter.hasNext()) {
