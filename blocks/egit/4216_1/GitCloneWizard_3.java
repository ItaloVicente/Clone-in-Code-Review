	boolean performMultipleClone() {
		final Object[] uris = repositorySelection.getURIs();
		setWindowTitle(UIText.GitCloneWizard_jobName);
		final String workdirLocation = repositorySelection.getDestination();
		if (uris == null) {
			return true;
		}

		Job job = new Job("Cloning") { //$NON-NLS-1$

			@Override
			public IStatus run(IProgressMonitor monitor) {
				for (Object uriString : uris) {
					URIish uri = null;
					try {
						uri = new URIish((String) uriString);
					} catch (URISyntaxException e) {
					}
					if (uri == null) {
						continue;
					}
					Collection<Ref> selectedBranches;
					selectedBranches = getAvailableBranches(uri);

					final File workdir = new File(workdirLocation
							+ "/" + uri.getPath()); //$NON-NLS-1$

					final Ref ref = head;
					final String remoteName = "orignial"; //$NON-NLS-1$

					boolean created = workdir.exists();
					if (!created)
						created = workdir.mkdirs();

					if (!created || !workdir.isDirectory()) {
						final String errorMessage = NLS.bind(
								UIText.GitCloneWizard_errorCannotCreate,
								workdir.getPath());
						Status status = new Status(IStatus.ERROR,
								Activator.getPluginId(), 0, errorMessage, null);
						return status;
					}

					int timeout = Activator.getDefault().getPreferenceStore()
							.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
					final CloneOperation op = new CloneOperation(uri, true,
							selectedBranches, workdir,
							ref != null ? ref.getName() : null, remoteName,
							timeout);



					runAsJob(uri, op);
				}
				return Status.OK_STATUS;
			}
		};

		job.schedule();

		return true;
	}

	public List<Ref> getAvailableBranches(URIish uri) {
		final ListRemoteOperation listRemoteOp;

		try {
			final Repository db = new FileRepository(new File("/tmp")); //$NON-NLS-1$
			int timeout = Activator.getDefault().getPreferenceStore()
					.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
			listRemoteOp = new ListRemoteOperation(db, uri, timeout);

			Job job = new Job("Retrieving remote branches") { //$NON-NLS-1$

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						listRemoteOp.run(monitor);
					} catch (InvocationTargetException e) {
						return new Status(IStatus.ERROR,
								Activator.getPluginId(), "Error");//$NON-NLS-1$
					} catch (InterruptedException e) {
						return Status.CANCEL_STATUS;
					}
					return Status.OK_STATUS;
				}
			};
			job.schedule();
			job.join();

			final Ref idHEAD = listRemoteOp.getRemoteRef(Constants.HEAD);
			head = null;
			List<Ref> availableRefs = new ArrayList<Ref>();
			for (final Ref r : listRemoteOp.getRemoteRefs()) {
				final String n = r.getName();
				if (!n.startsWith(Constants.R_HEADS))
					continue;
				availableRefs.add(r);
				if (idHEAD == null || head != null)
					continue;
				if (r.getObjectId().equals(idHEAD.getObjectId()))
					head = r;
			}
			Collections.sort(availableRefs, new Comparator<Ref>() {
				public int compare(final Ref o1, final Ref o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			if (idHEAD != null && head == null) {
				head = idHEAD;
				availableRefs.add(0, idHEAD);
			}
			return availableRefs;
		} catch (IOException e) {
		} catch (InterruptedException e) {
		}
		return null;
	}

