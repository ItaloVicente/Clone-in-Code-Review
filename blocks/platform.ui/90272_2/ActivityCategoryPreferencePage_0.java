        table.addSelectionListener(widgetSelectedAdapter(e -> {
			if (e.detail == SWT.CHECK) {
				TableItem tableItem = (TableItem) e.item;

				ICategory category = (ICategory) tableItem.getData();
				if (isLocked(category)) {
					tableItem.setChecked(true);
					e.doit = false; // veto the check
					return;
				}
				Set activitySet = WorkbenchActivityHelper
						.getActivityIdsForCategory(category);
				if (tableItem.getChecked()) {
					activitySet.addAll(workingCopy.getEnabledActivityIds());
				} else {
					HashSet newSet = new HashSet(workingCopy
							.getEnabledActivityIds());
					newSet.removeAll(activitySet);
					activitySet = newSet;
