			if (previouslyChecked != null && previouslyChecked.length > 0) {
				configsTable.setCheckedElements(previouslyChecked);
			} else {
				if (filterGroups.size() > 0) {
					Object group = filterGroups.iterator().next();
					configsTable.setChecked(group, true);
				}
