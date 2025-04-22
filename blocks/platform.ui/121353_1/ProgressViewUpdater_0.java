			JobTreeElement[] updateItems = refreshes.toArray(new JobTreeElement[0]);
			JobTreeElement[] additionItems = additions.toArray(new JobTreeElement[0]);
			JobTreeElement[] deletionItems = deletions.toArray(new JobTreeElement[0]);
			return new JobTreeElement[][] { updateItems, additionItems, deletionItems };
		}

