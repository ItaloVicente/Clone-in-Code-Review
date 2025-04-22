		Iterator it = selection.iterator();
		Collection result = null;
		while (it.hasNext()) {
			Object element = it.next();
			Collection collection = getApplicableContributors(element);
			if (result == null)
				result = new LinkedHashSet(collection);
			else
