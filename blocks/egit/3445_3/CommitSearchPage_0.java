		GridDataFactory.swtDefaults().grab(true, false)
				.applyTo(this.searchAllBranchesButton);

		Button toggleButton = new Button(repositoryGroup, SWT.PUSH);
		toggleButton.setText(UIText.CommitSearchPage_ToggleSelection);
		toggleButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				for (TableItem item : repositoryViewer.getTable().getItems())
					repositoryViewer.setChecked(item.getData(),
							!item.getChecked());
				repositoryGroup.setText(getRepositoryText());
			}

		});
		repositoryGroup.setText(getRepositoryText());
	}

	private String getRepositoryText() {
		return MessageFormat.format(UIText.CommitSearchPage_Repositories,
				Integer.valueOf(repositoryViewer.getCheckedElements().length),
				Integer.valueOf(repositoryViewer.getTable().getItemCount()));
