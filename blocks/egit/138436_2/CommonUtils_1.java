			String name1 = o1.getName();
			String name2 = o2.getName();
			if (name1.startsWith(Constants.R_TAGS)
					&& o2.getName().startsWith(Constants.R_TAGS)
					&& !sortTagsAscending) {
				name1 = o2.getName();
				name2 = o1.getName();
			}
			return STRING_ASCENDING_COMPARATOR.compare(name1, name2);
