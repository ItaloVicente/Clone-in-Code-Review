        table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.detail == SWT.CHECK) {
					TableItem tableItem = (TableItem) e.item;

					ICategory category = (ICategory) tableItem.getData();
					if (isLocked(category)) {
						tableItem.setChecked(true);
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
					}

					workingCopy.setEnabledActivityIds(activitySet);
				}
			}
		});
