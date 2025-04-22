
	private void registerAutoIgnoreDerivedResources() {
		ignoreDerivedResourcesListener = new IgnoreDerivedResources();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				ignoreDerivedResourcesListener,
				IResourceChangeEvent.POST_CHANGE
						| IResourceChangeEvent.POST_BUILD);
	}

	private static class IgnoreDerivedResources implements
			IResourceChangeListener {
		private static int INTERESTING_CHANGES = IResourceDelta.ADDED
				| IResourceDelta.DERIVED_CHANGED;

		protected boolean doAutoIgnoreDerived() {
			return true;
		}

		public void resourceChanged(IResourceChangeEvent event) {
			try {
				IResourceDelta d = event.getDelta();
				if (d == null)
					return;
				d.accept(new IResourceDeltaVisitor() {

					public boolean visit(IResourceDelta delta)
							throws CoreException {
						if (!doAutoIgnoreDerived())
							return false;
						if ((delta.getFlags() & INTERESTING_CHANGES) == 0)
							return true;
						final IResource r = delta.getResource();
						IPath p = r.getLocation();
						if (p.toString().indexOf("bin") >= 0) //$NON-NLS-1$
							System.out.println(p);
						if (r.isDerived()) {
							System.out.println(p);
						}
						return false;
					}

				});
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
				return;
			}

		}

	}
