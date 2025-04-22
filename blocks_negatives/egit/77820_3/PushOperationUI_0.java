		Job job = new RepositoryJob(NLS.bind(UIText.PushOperationUI_PushJobName,
				destinationString)) {

			private PushOperationResult result;

			@Override
			protected IStatus performJob(IProgressMonitor monitor) {
				try {
					result = execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(e.getStatus()
							.getMessage(), e);
				}
				return Status.OK_STATUS;
			}

			@Override
			protected IAction getAction() {
				if (expectedResult == null || !expectedResult.equals(result)) {
					return new ShowPushResultAction(repo, result,
							destinationString, showConfigureButton);
				}
				return null;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.PUSH.equals(family)) {
					return true;
				}
				return super.belongsTo(family);
			}

		};
