		}

		contributors = removeDups(contributors);

		return contributors.isEmpty() ? Collections.EMPTY_LIST : new ArrayList(contributors);
	}

	protected List getObjectContributors(Class objectClass) {
