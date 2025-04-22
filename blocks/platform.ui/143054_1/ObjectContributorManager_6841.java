	}

	protected List getContributors(List elements) {
		List commonAdapters = new ArrayList();
		List commonClasses = getCommonClasses(elements, commonAdapters);

		Class resourceClass = getCommonResourceClass(elements);
		Class resourceMappingClass = getResourceMappingClass(elements);


		List contributors = new ArrayList();

		if (resourceClass != null) {
			addAll(contributors, getResourceContributors(resourceClass));
		}
		if (commonClasses != null && !commonClasses.isEmpty()) {
			for (int i = 0; i < commonClasses.size(); i++) {
				List results = getObjectContributors((Class) commonClasses.get(i));
				addAll(contributors, results);
			}
		}
		if (resourceMappingClass == null) {
			resourceMappingClass = LegacyResourceSupport.getResourceMappingClass();
			if (resourceMappingClass != null && commonAdapters.contains(resourceMappingClass.getName())) {
				addAll(contributors, getResourceContributors(resourceMappingClass));
			}
		} else {
			contributors.addAll(getResourceContributors(resourceMappingClass));
		}
		if (!commonAdapters.isEmpty()) {
			for (Iterator it = commonAdapters.iterator(); it.hasNext();) {
				String adapter = (String) it.next();
				addAll(contributors, getAdaptableContributors(adapter));
			}
		}

		contributors = removeDups(contributors);

		return contributors.isEmpty() ? Collections.EMPTY_LIST : new ArrayList(contributors);
	}

