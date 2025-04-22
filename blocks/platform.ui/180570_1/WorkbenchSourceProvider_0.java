			updateActivePerspective(id);
		}

		@Override
		public void perspectiveClosed(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
			if (page.getPerspective() == null) {
				updateActivePerspective(null);
			}
		}

		private void updateActivePerspective(String id) {
