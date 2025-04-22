		Iterator iterator = subPages.iterator();
		while (iterator.hasNext()) {
			RegistryPageContributor next = (RegistryPageContributor) iterator.next();
			if (next.getPageId().equals(id))
				return next;
		}
		return null;
