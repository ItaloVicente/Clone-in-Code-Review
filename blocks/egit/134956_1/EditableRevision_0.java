		Runnable runnable = () -> {
			for (IContentChangeListener listener : listeners) {
				SafeRunnable.run(
						() -> listener.contentChanged(EditableRevision.this));
			}
		};
		if (Display.getCurrent() == null) {
			Display.getDefault().syncExec(runnable);
		} else {
			runnable.run();
		}
