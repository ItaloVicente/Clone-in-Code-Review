			MenuItem doFetch = new MenuItem(men, SWT.PUSH);
			doFetch.setText(UIText.RepositoriesView_FetchMenu);
			doFetch.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent evt) {
					new FetchConfiguredRemoteAction(node.getRepository(),
							configName).run(getSite().getShell());
				}

			});

