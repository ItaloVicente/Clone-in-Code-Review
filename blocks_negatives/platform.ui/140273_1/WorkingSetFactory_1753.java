        String workingSetEditPageId = memento
                .getString(IWorkbenchConstants.TAG_EDIT_PAGE_ID);
        String aggregateString = memento
				.getString(AbstractWorkingSet.TAG_AGGREGATE);
		boolean isAggregate = aggregateString != null
				&& Boolean.parseBoolean(aggregateString);
