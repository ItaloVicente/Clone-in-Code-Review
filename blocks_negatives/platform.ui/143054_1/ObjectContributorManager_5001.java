    	}

        contributors = removeDups(contributors);

    	return contributors.isEmpty() ? Collections.EMPTY_LIST : new ArrayList(contributors);
    }

    /**
     * Returns the contributions for the given class. This considers
     * contributors on any super classes and interfaces.
     *
     * @param objectClass the class to search for contributions.
     * @return the contributions for the given class. This considers
     * contributors on any super classes and interfaces.
     *
     * @since 3.1
     */
    protected List getObjectContributors(Class objectClass) {
