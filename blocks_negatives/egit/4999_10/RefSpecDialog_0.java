	private List<Ref> getRefsForContentAssist(boolean source) {
		if (source) {
			if (sourceRefs != null)
				return sourceRefs;
		} else if (destinationRefs != null)
			return destinationRefs;

		List<Ref> result = new ArrayList<Ref>();
		try {
			boolean local = pushMode == source;
			if (!local) {
				URIish uriToCheck;
				if (pushMode) {
					if (config.getPushURIs().isEmpty())
						uriToCheck = config.getURIs().get(0);
					else
						uriToCheck = config.getPushURIs().get(0);
				} else
					uriToCheck = config.getURIs().get(0);
				final ListRemoteOperation lop = new ListRemoteOperation(repo,
						uriToCheck,
						Activator.getDefault().getPreferenceStore().getInt(
								UIPreferences.REMOTE_CONNECTION_TIMEOUT));

				new ProgressMonitorDialog(getShell()).run(false, true,
						new IRunnableWithProgress() {

							public void run(IProgressMonitor monitor)
									throws InvocationTargetException,
									InterruptedException {
								monitor
										.beginTask(
												UIText.RefSpecDialog_GettingRemoteRefsMonitorMessage,
												IProgressMonitor.UNKNOWN);
								lop.run(monitor);
								monitor.done();
							}
						});
				for (Ref ref : lop.getRemoteRefs()) {
					if (ref.getName().startsWith(Constants.R_HEADS)
							|| (!pushMode && ref.getName().startsWith(
									Constants.R_TAGS)))
						result.add(ref);
				}

			} else {
				if (pushMode)
					for (Ref ref : repo.getRefDatabase().getRefs(
							RefDatabase.ALL).values()) {
						if (ref.getName().startsWith(Constants.R_REMOTES)) {
							continue;
						}
						result.add(ref);
					}
				else
					for (Ref ref : repo.getRefDatabase().getRefs(
							Constants.R_REMOTES).values()) {
						result.add(ref);
					}
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			Activator.handleError(e.getMessage(), e, true);
			return result;
		}
		if (source)
			sourceRefs = result;
		else
			destinationRefs = result;
		return result;
	}
