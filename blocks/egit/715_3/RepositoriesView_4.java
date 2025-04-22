			MenuItem doPush = new MenuItem(men, SWT.PUSH);
			doPush.setText(UIText.RepositoriesView_DoPushMenuItem);
			doPush.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent evt) {
					new PushConfiguredRemoteAction(node.getRepository(),
							configName).run(getSite().getShell(), false);
				}
			});
