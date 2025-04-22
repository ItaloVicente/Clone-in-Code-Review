	private JobTreeElement blockedElement = new BlockedUIElement();

	/**
	 * The BlockedUIElement is the JobTreeElement that represents the blocked job in
	 * the dialog.
	 */
	private class BlockedUIElement extends JobTreeElement {
		@Override
		Object[] getChildren() {
			return ProgressManagerUtil.EMPTY_OBJECT_ARRAY;
		}

		@Override
		String getDisplayString() {
			if (blockedTaskName == null || blockedTaskName.isEmpty()) {
				return ProgressMessages.BlockedJobsDialog_UserInterfaceTreeElement;
			}
			return blockedTaskName;
		}

		@Override
		public Image getDisplayImage() {
			return JFaceResources.getImage(ProgressManager.WAITING_JOB_KEY);
		}

		@Override
		boolean hasChildren() {
			return false;
		}

		@Override
		boolean isActive() {
			return true;
		}

		@Override
		boolean isJobInfo() {
			return false;
		}

		@Override
		public void cancel() {
			blockingMonitor.setCanceled(true);
		}

		@Override
		public boolean isCancellable() {
			return true;
		}
	}

