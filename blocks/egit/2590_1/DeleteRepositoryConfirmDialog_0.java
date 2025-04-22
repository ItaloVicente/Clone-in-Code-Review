		if (numberOfProjects > 0) {
			removeProjects.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shouldRemoveProjects = removeProjects.getSelection();
				}
			});
			GridDataFactory.fillDefaults().grab(true, false).applyTo(removeProjects);
			removeProjects
				.setText(NLS
						.bind(UIText.RepositoriesView_ConfirmProjectDeletion_Question,
								Integer.valueOf(numberOfProjects)));
		} else
			removeProjects.setVisible(false);
