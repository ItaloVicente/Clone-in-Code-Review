			URIish uri = new URIish(uriCombo.getText());
			Ref currentHead = repository.exactRef(Constants.HEAD);
			String ref = prefixCombo.getItem(prefixCombo.getSelectionIndex())
					+ branchText.getText().trim();
			if (topicText.isEnabled()) {
				ref = setTopicInRef(ref, topicText.getText().trim());
			}
			RemoteRefUpdate update = new RemoteRefUpdate(repository,
					currentHead, ref, false, null, null);
			PushOperationSpecification spec = new PushOperationSpecification();

			spec.addURIRefUpdates(uri, Arrays.asList(update));
			final PushOperationUI op = new PushOperationUI(repository, spec,
					false);
			op.setCredentialsProvider(new EGitCredentialsProvider());
			final PushOperationResult[] result = new PushOperationResult[1];
			getContainer().run(true, true, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					try {
						result[0] = op.execute(monitor);
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
