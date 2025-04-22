			public void widgetSelected(final SelectionEvent e) {
				if (e.detail != SWT.CHECK)
					return;

				final TableItem tableItem = (TableItem) e.item;
				final int i = refsTable.indexOf(tableItem);
				final Ref ref = availableRefs.get(i);

				if (tableItem.getChecked()) {
					int insertionPos = 0;
					for (int j = 0; j < i; j++) {
						if (selectedRefs.contains(availableRefs.get(j)))
							insertionPos++;
					}
					selectedRefs.add(insertionPos, ref);
				} else
					selectedRefs.remove(ref);
