			});

			if (fetchExists) {
				MenuItem deleteFetch = new MenuItem(men, SWT.PUSH);
				deleteFetch.setText(UIText.RepositoriesView_RemoveFetch_menu);
				deleteFetch.addSelectionListener(new SelectionAdapter() {
