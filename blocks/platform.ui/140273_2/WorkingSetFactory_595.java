		if (workingSetEditPageId != null) {
			workingSet.setId(workingSetEditPageId);
		} else if (!isAggregate) {
			workingSet.setId("org.eclipse.ui.resourceWorkingSetPage"); //$NON-NLS-1$
		}
		return workingSet;
	}
