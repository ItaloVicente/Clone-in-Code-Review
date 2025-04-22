	/**
	 * Part listener which cleans up this page when the source part is closed.
	 * This is hooked only when there is a source part.
	 *
	 * @since 3.2
	 */
	private class PartListener implements IPartListener {
		@Override
		public void partActivated(IWorkbenchPart part) {
		}

		@Override
		public void partBroughtToTop(IWorkbenchPart part) {
		}

		@Override
		public void partClosed(IWorkbenchPart part) {
			if (sourcePart == part) {
				if (sourcePart != null)
					sourcePart.getSite().getPage().removePartListener(partListener);
				sourcePart = null;
				if (viewer != null && !viewer.getControl().isDisposed()) {
					viewer.setInput(new Object[0]);
				}
			}
		}

		@Override
		public void partDeactivated(IWorkbenchPart part) {
		}

		@Override
		public void partOpened(IWorkbenchPart part) {
		}
	}

	private PartListener partListener = new PartListener();

