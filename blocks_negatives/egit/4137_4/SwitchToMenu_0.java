				if (itemCount > MAX_NUM_MENU_ENTRIES)
					break;
				MenuItem item = new MenuItem(menu, SWT.PUSH);
				item.setText(entry.getKey());
				boolean checkedOut = currentBranch.equals(entry.getValue()
						.getName());
				if (checkedOut)
					item.setImage(checkedOutImage);
				else
					item.setImage(branchImage);
				item.setEnabled(!checkedOut);
				item.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						BranchOperationUI.checkout(repository,
								entry.getValue().getName()).start();
					}
				});
