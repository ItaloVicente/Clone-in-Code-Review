	private class ActivationJob implements Runnable {

		/**
		 * Returns whether it is acceptable for a stack to be activated. As the
		 * activation occurs asynchronously, the original activation request may
		 * have been invalidated since the request was originally enqueued.
		 * <p>
		 * For example, an activation request that was enqueued no longer should
		 * be honoured if a dialog window gets opened in the interim.
		 * </p>
		 * 
		 * @return <code>true</code> if the requested stack should be activated,
		 *         <code>false</code> otherwise
		 */
		private boolean shouldActivate() {
			if (application != null) {
				IEclipseContext applicationContext = application.getContext();
				IEclipseContext activeChild = applicationContext
						.getActiveChild();
				if (activeChild == null
						|| activeChild.get(MWindow.class) != application
								.getSelectedElement()
						|| application.getSelectedElement() != modelService
								.getTopLevelWindowFor(stackToActivate)) {
					return false;
				}
			}
			return true;
		}

		public MElementContainer<MUIElement> stackToActivate = null;

		public void run() {
			activationJob = null;
			if (stackToActivate != null
					&& stackToActivate.getSelectedElement() != null
					&& shouldActivate()) {
				int location = modelService.getElementLocation(stackToActivate);
				if ((location & EModelService.IN_ACTIVE_PERSPECTIVE) == 0
						&& (location & EModelService.OUTSIDE_PERSPECTIVE) == 0
						&& (location & EModelService.IN_SHARED_AREA) == 0)
					return;

				MUIElement selElement = stackToActivate.getSelectedElement();
				if (!isValid(selElement))
					return;

				if (selElement instanceof MPlaceholder)
					selElement = ((MPlaceholder) selElement).getRef();
				activate((MPart) selElement);
			}
		}
	}

