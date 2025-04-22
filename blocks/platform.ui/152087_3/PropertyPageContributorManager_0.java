		boolean isMultiSelection = selection.size() > 1;
		Iterator<Object> selIter = selection.iterator();
		Collection<RegistryPageContributor> result = null;
		while (selIter.hasNext()) {
			Object selectionElement = selIter.next();
			Collection<RegistryPageContributor> collection = getApplicableContributors(selectionElement);
			if (isMultiSelection) {
				Iterator<RegistryPageContributor> resIter = collection.iterator();
				while (resIter.hasNext()) {
					RegistryPageContributor contrib = resIter.next();
					if (!contrib.supportsMultipleSelection()) {
						resIter.remove();
					}
				}
			}
			if (result == null) {
				result = new LinkedHashSet<>(collection);
			} else {
