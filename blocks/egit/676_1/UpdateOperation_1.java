	public void execute(IProgressMonitor monitor) throws CoreException {
		IProgressMonitor m;
		if (monitor == null)
			m = new NullProgressMonitor();
		else
			m = monitor;

		trace("running"); //$NON-NLS-1$
		try {
			final IdentityHashMap<RepositoryMapping, Boolean> tomerge = new IdentityHashMap<RepositoryMapping, Boolean>();
			try {
				final int[] count=new int[1];
				long t0=System.currentTimeMillis();
				for (IResource obj : rsrcList) {
					if (obj instanceof IContainer) {
						((IContainer)obj).accept(new IResourceProxyVisitor() {
							public boolean visit(IResourceProxy rp) throws CoreException {
								if (rp.getType() == IResource.FILE) {
									count[0]++;
								}
								return true;
							}
						}, IContainer.EXCLUDE_DERIVED);
					} else {
						count[0]++;
					}
				}
				long t1=System.currentTimeMillis();
				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.CORE.getLocation(),
							"Counted " + count[0] //$NON-NLS-1$
									+ " items to update in " //$NON-NLS-1$
									+ (t1 - t0) / 1000.0 + "s"); //$NON-NLS-1$
				m.beginTask(CoreText.UpdateOperation_updating, count[0]);
				final IProgressMonitor fm = m;
				for (IResource r : rsrcList) {
					final RepositoryMapping rm = RepositoryMapping.getMapping(r);
					final GitIndex index = rm.getRepository().getIndex();
					tomerge.put(rm, Boolean.TRUE);
					if (r instanceof IContainer) {
						((IContainer)r).accept(new IResourceVisitor() {
							public boolean visit(IResource resource) throws CoreException {
								try {
									if (resource.getType() == IResource.FILE) {
										String path = rm.getRepoRelativePath(resource);
										Entry entry = index.getEntry(path);
										if (entry != null) {
											entry.update(new File(rm.getWorkDir(),path));
										}
										fm.worked(1);
									}
								} catch (IOException e) {
									if (GitTraceLocation.CORE.isActive())
										GitTraceLocation.getTrace().trace(GitTraceLocation.CORE.getLocation(), e.getMessage(), e);
									throw new CoreException(Activator.error(CoreText.UpdateOperation_failed, e));
								}
								return true;
							}
						},IResource.DEPTH_INFINITE, IContainer.EXCLUDE_DERIVED);
					} else {
						String path = rm.getRepoRelativePath(r);
						Entry entry = index.getEntry(path);
						if (entry != null) {
							entry.update(new File(rm.getWorkDir(),path));
						}
						m.worked(1);
					}
				}
				for (RepositoryMapping rm : tomerge.keySet()) {
					m.setTaskName(NLS.bind(CoreText.UpdateOperation_writingIndex, rm
							.getRepository().getDirectory()));
					rm.getRepository().getIndex().write();
				}
			} catch (NotSupportedException e) {
				throw new CoreException(Activator.error(e.getMessage(),e));
			} catch (RuntimeException e) {
				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(GitTraceLocation.CORE.getLocation(), e.getMessage(), e);
				throw new CoreException(Activator.error(CoreText.UpdateOperation_failed, e));
			} catch (IOException e) {
				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(GitTraceLocation.CORE.getLocation(), e.getMessage(), e);
				throw new CoreException(Activator.error(CoreText.UpdateOperation_failed, e));
			} catch (CoreException e) {
				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(GitTraceLocation.CORE.getLocation(), e.getMessage(), e);
				throw new CoreException(Activator.error(CoreText.UpdateOperation_failed, e));
			} finally {
				try {
					final Iterator i = tomerge.keySet().iterator();
					while (i.hasNext()) {
						final RepositoryMapping r = (RepositoryMapping) i.next();
						r.getRepository().getIndex().read();
						r.fireRepositoryChanged();
					}
				} catch (IOException e) {
					if (GitTraceLocation.CORE.isActive())
						GitTraceLocation.getTrace().trace(GitTraceLocation.CORE.getLocation(), e.getMessage(), e);
				} finally {
					m.done();
				}
			}
		} finally {
			trace("done");  //$NON-NLS-1$
			m.done();
		}
	}

	private void trace(final String m) {
		if (GitTraceLocation.CORE.isActive())
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.CORE.getLocation(), "(UpdateOperation)" + m); //$NON-NLS-1$
