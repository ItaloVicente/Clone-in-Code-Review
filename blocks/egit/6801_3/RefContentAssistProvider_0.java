
	private class FetchPushDestinationRefsJob extends Job {
		List<Ref> result;

		public FetchPushDestinationRefsJob() {
			super(UIText.RefSpecDialog_GettingRemoteRefsMonitorMessage);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			List<Ref> tempResult;
			try {
				tempResult = new ArrayList<Ref>();
				ListRemoteOperation lop = new ListRemoteOperation(repo,
						uri,
						Activator.getDefault().getPreferenceStore().getInt(
								UIPreferences.REMOTE_CONNECTION_TIMEOUT));

				lop.run(monitor);
				for (Ref ref : lop.getRemoteRefs())
					if (ref.getName().startsWith(Constants.R_HEADS)
							|| (!pushMode && ref.getName().startsWith(
									Constants.R_TAGS)))
						tempResult.add(ref);
			} catch (InvocationTargetException e) {
				return new Status(IStatus.ERROR, Activator.getPluginId(), e.getLocalizedMessage(), e);
			} catch (InterruptedException e) {
				return Status.CANCEL_STATUS;
			}

			this.result= tempResult;
			return Status.OK_STATUS;
		}
	}

