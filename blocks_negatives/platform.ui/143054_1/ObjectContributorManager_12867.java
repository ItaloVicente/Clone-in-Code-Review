    }

    /**
     * Returns the list of contributors that are interested in the
     * given list of model elements.
     * @param elements a list of model elements (<code>Object</code>)
     * @return the list of interested contributors (<code>IObjectContributor</code>)
     */
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
                List results = getObjectContributors((Class) commonClasses
                        .get(i));
                addAll(contributors, results);
            }
        }
        if (resourceMappingClass == null) {
            resourceMappingClass = LegacyResourceSupport
                    .getResourceMappingClass();
            if (resourceMappingClass != null
                    && commonAdapters.contains(resourceMappingClass.getName())) {
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

    /**
	 * Adds all items in toAdd to the given collection.  Optimized to avoid creating an iterator.
	 * This assumes that toAdd is efficient to index (i.e. it's an ArrayList or some other RandomAccessList),
	 * which is the case for all uses in this class.
