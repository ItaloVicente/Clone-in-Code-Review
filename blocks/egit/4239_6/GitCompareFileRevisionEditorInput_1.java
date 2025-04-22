	private class InternalResourceSaveableComparison extends
			LocalResourceSaveableComparison implements
			ISharedDocumentAdapterListener {
		private LocalResourceTypedElement lrte;

		private boolean connected = false;

		public InternalResourceSaveableComparison(ICompareInput input,
				CompareEditorInput editorInput) {
			super(input, editorInput, left);
			ITypedElement element = left;
			if (element instanceof LocalResourceTypedElement) {
				lrte = (LocalResourceTypedElement) element;
				if (lrte.isConnected()) {
					registerSaveable(true);
				} else {
					lrte.setSharedDocumentListener(this);
				}
			}
		}

		protected void fireInputChange() {
			GitCompareFileRevisionEditorInput.this.fireInputChange();
		}

		public void dispose() {
			super.dispose();
			if (lrte != null)
				lrte.setSharedDocumentListener(null);
		}

		public void handleDocumentConnected() {
			if (connected)
				return;
			connected = true;
			registerSaveable(false);
			if (lrte != null)
				lrte.setSharedDocumentListener(null);
		}

		private void registerSaveable(boolean init) {
			ICompareContainer container = getContainer();
			IWorkbenchPart part = container.getWorkbenchPart();
			if (part != null) {
				ISaveablesLifecycleListener lifecycleListener = getSaveablesLifecycleListener(part);
				if (!init)
					lifecycleListener
							.handleLifecycleEvent(new SaveablesLifecycleEvent(
									part, SaveablesLifecycleEvent.POST_CLOSE,
									new Saveable[] { this }, false));
				initializeHashing();
				lifecycleListener
						.handleLifecycleEvent(new SaveablesLifecycleEvent(part,
								SaveablesLifecycleEvent.POST_OPEN,
								new Saveable[] { this }, false));
			}
		}

		private ISaveablesLifecycleListener getSaveablesLifecycleListener(
				IWorkbenchPart part) {
			ISaveablesLifecycleListener listener = (ISaveablesLifecycleListener) Utils
					.getAdapter(part, ISaveablesLifecycleListener.class);
			if (listener == null)
				listener = (ISaveablesLifecycleListener) part.getSite()
						.getService(ISaveablesLifecycleListener.class);
			return listener;
		}

		public void handleDocumentDeleted() {
		}

		public void handleDocumentDisconnected() {
		}

		public void handleDocumentFlushed() {
		}

		public void handleDocumentSaved() {
		}
	}
