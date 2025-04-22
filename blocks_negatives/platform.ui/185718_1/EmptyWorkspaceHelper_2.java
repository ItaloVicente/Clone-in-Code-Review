		public void resourceChanged(IResourceChangeEvent event) {

			IResourceDelta resourceDelta = event.getDelta();
			if (resourceDelta != null) {

				IResourceDelta[] affectedChildren = resourceDelta.getAffectedChildren();
				for (IResourceDelta affectedChildResourceDelta : affectedChildren) {
					IResource resource = affectedChildResourceDelta.getResource();
					int kind = affectedChildResourceDelta.getKind();
					if (resource instanceof IProject
							&& (kind == IResourceDelta.ADDED || kind == IResourceDelta.REMOVED)) {
						PlatformUI.getWorkbench().getDisplay().asyncExec(
								() -> PlatformUI.getWorkbench().getDisplay().timerExec(200, switchTopControlRunnable));
						return;
					}
				}
