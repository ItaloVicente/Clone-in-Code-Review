	public void run(IProgressMonitor actMonitor) throws InvocationTargetException {

		if (operationResult != null)
			throw new IllegalStateException(CoreText.OperationAlreadyExecuted);

		for (URIish uri : this.specification.getURIs()) {
			for (RemoteRefUpdate update : this.specification.getRefUpdates(uri))
				if (update.getStatus() != Status.NOT_ATTEMPTED)
					throw new IllegalStateException(
							CoreText.RemoteRefUpdateCantBeReused);
		}
		IProgressMonitor monitor;
		if (actMonitor == null)
