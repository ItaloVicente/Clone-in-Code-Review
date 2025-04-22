	private static class ContextListener extends RunAndTrack {

		private AtomicBoolean stopped;

		private Map<?, ?> cache;

		ContextListener(AtomicBoolean stopped, Map<?, ?> cache) {
			super();
			this.stopped = stopped;
			this.cache = cache;
		}

		private Object lastSelection;

		private Object lastMenuSelection;

		@Override
		public boolean changed(IEclipseContext context) {
			if (stopped.get()) {
				cache.clear();
				return false;
			}
			Object selection = context
					.get(ISources.ACTIVE_CURRENT_SELECTION_NAME);
			if (selection instanceof ITextSelection) {
				selection = getInput(context);
			}
			Object menuSelection = context
					.getActive(ISources.ACTIVE_MENU_SELECTION_NAME);
			if (menuSelection instanceof ITextSelection) {
				menuSelection = getInput(context);
			}
			if (selection != lastSelection
					|| menuSelection != lastMenuSelection) {
				cache.clear();
			}
			lastSelection = selection;
			lastMenuSelection = menuSelection;
			return true;
		}

		private Object getInput(IEclipseContext context) {
			Object[] input = { null };
			runExternalCode(() -> {
				IEditorInput e = getEditorInput(context);
				input[0] = e != null ? e : StructuredSelection.EMPTY;
			});
			return input[0];
		}
