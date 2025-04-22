
		return itemCount;
	}

	private Set<String> getBranchNameIntersection(
			List<Map<String, Ref>> refMapping) {
		Set<String> intersection = null;

		for (Map<String, Ref> refs : refMapping) {
			if (null == intersection) {
				intersection = new TreeSet<>(
						CommonUtils.STRING_ASCENDING_COMPARATOR);
				intersection.addAll(refs.keySet());
			} else {
				intersection.retainAll(refs.keySet());
			}
		}

		return intersection;
	}

	private void createOtherMenuItem(Menu menu, Repository repository) {
		MenuItem others = new MenuItem(menu, SWT.PUSH);
		others.setText(UIText.SwitchToMenu_OtherMenuLabel);
		others.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CheckoutDialog dialog = new CheckoutDialog(
						e.display.getActiveShell(), repository);
				if (dialog.open() == Window.OK) {
					BranchOperationUI.checkout(repository, dialog.getRefName())
							.start();
				}

			}
		});
	}

	private void createDisabledMenu(Menu menu, String text) {
		MenuItem disabled = new MenuItem(menu, SWT.PUSH);
		disabled.setText(text);
		disabled.setImage(branchImage);
		disabled.setEnabled(false);
