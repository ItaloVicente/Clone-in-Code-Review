		for (final RefNode node : nodes) {
			final Ref ref = node.getObject();

			try {
				new ProgressMonitorDialog(shell).run(false, false,
						new IRunnableWithProgress() {
							public void run(IProgressMonitor monitor)
									throws InvocationTargetException,
									InterruptedException {
								try {
									RefUpdate op = node.getRepository()
											.updateRef(ref.getName());
									op.setRefLogMessage("branch deleted", //$NON-NLS-1$
											false);
									op.setForceUpdate(true);
									op.delete();
								} catch (IOException ioe) {
									throw new InvocationTargetException(ioe);
								}
