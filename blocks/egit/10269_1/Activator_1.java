
	private void registerAutoIgnoreDerivedResources() {
		ignoreDerivedResourcesListener = new IgnoreDerivedResources();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				ignoreDerivedResourcesListener,
				IResourceChangeEvent.POST_CHANGE);
	}

	private static class IgnoreDerivedResources implements
			IResourceChangeListener {

		protected boolean autoIgnoreDerived() {
			return true;
		}

		public void resourceChanged(IResourceChangeEvent event) {
			try {
				IResourceDelta d = event.getDelta();
				if (d == null)
					return;

				final List<IPath> toBeIgnored = new ArrayList<IPath>();

				d.accept(new IResourceDeltaVisitor() {

					public boolean visit(IResourceDelta delta)
							throws CoreException {
						if (!autoIgnoreDerived())
							return false;
						int flags = delta.getFlags();
						if ((flags != 0)
								&& (flags & (IResourceDelta.ADDED | IResourceDelta.DERIVED_CHANGED)) == 0)
							return true;

						final IResource r = delta.getResource();
						if (r.isTeamPrivateMember())
							return false;

						if (r.isDerived()) {
							try {
								if (!RepositoryUtil.isIgnored(r.getLocation()))
									toBeIgnored.add(r.getLocation());
							} catch (IOException e) {
								logError(
										MessageFormat.format(
												CoreText.Activator_ignoreResourceFailed,
												r.getFullPath()), e);
							}
							return false;
						}
						return true;
					}
				});

				JobUtil.scheduleUserJob(new IgnoreOperation(toBeIgnored),
						CoreText.Activator_autoIgnoreDerivedResources,
						JobFamilies.AUTO_IGNORE);
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
				return;
			}
		}

	}
