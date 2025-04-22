	static class PushJob extends RepositoryJob {
		private final PushOperation operation;

		private final PushOperationResult resultToCompare;

		private final String destinationString;

		private Repository localDb;

		private PushOperationResult operationResult;

		public PushJob(final Repository localDb, final PushOperation operation,
				final PushOperationResult resultToCompare,
				final String destinationString) {
			super(NLS.bind(UIText.PushWizard_jobName, getURIsString(operation
					.getSpecification().getURIs())));
			this.operation = operation;
			this.resultToCompare = resultToCompare;
			this.destinationString = destinationString;
			this.localDb = localDb;
		}

		@Override
		protected IStatus performJob(final IProgressMonitor monitor) {
			try {
				operation.run(monitor);
			} catch (final InvocationTargetException e) {
				return new Status(IStatus.ERROR, Activator.getPluginId(),
						UIText.PushWizard_unexpectedError, e.getCause());
			}

			operationResult = operation.getOperationResult();
			if (!operationResult.isSuccessfulConnectionForAnyURI()) {
				return new Status(IStatus.ERROR, Activator.getPluginId(),
						NLS.bind(UIText.PushWizard_cantConnectToAny,
								operationResult.getErrorStringForAllURis()));
			}

			return Status.OK_STATUS;
		}

		@Override
		protected IAction getAction() {
			Repository repo = localDb;
			if (repo != null && (resultToCompare == null
					|| !resultToCompare.equals(operationResult))) {
				return new ShowPushResultAction(repo, operationResult,
						destinationString, true);
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

	}
