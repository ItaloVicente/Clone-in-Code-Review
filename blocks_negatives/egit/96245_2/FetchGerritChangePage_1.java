		if (changeRefs == null) {
			final String uriText = uriCombo.getText();
			getContainer().run(true, true,
					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							ListRemoteOperation listOp;
							try {
								listOp = new ListRemoteOperation(
										repository,
										new URIish(uriText),
										Activator
												.getDefault()
												.getPreferenceStore()
												.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT));
							} catch (URISyntaxException e) {
								throw new InvocationTargetException(e);
							}

							listOp.run(monitor);
							changeRefs = new ArrayList<>();
							for (Ref ref : listOp.getRemoteRefs()) {
								Change change = Change.fromRef(ref.getName());
								if (change != null)
									changeRefs.add(change);
