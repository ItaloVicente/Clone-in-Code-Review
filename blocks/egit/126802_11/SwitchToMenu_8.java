
		return itemCount;
	}

	private Set<String> getBranchNameIntersection(
			List<Map<String, Ref>> refMapping) {
		Iterator<Map<String, Ref>> iterator = refMapping.iterator();
		if (!iterator.hasNext()) {
			return Collections.emptySet();
		}

		Set<String> intersection = new TreeSet<>(
				CommonUtils.STRING_ASCENDING_COMPARATOR);
		intersection.addAll(iterator.next().keySet());
		iterator.forEachRemaining(map -> intersection.retainAll(map.keySet()));
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
