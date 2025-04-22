			implements IResourceChangeListener, IPerspectiveListener, IPropertyChangeListener, DisposeListener {

		/**
		 * Listener to switch between the "original" control and the empty area. If no
		 * projects exist in the workspace the empty area is shown. If at least one
		 * project exists in the workspace the "original" control is shown.
		 *
		 * @noreference This method is not intended to be referenced by clients.
		 */
		@Override
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
			}
		}
