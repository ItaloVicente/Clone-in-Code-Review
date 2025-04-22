		activeListeners.clear();
		for (int i = 0; i < listeners.size(); i++) {
			final TransferDragSourceListener listener = listeners
					.get(i);
			event.doit = true; // restore event.doit
			SafeRunnable.run(new SafeRunnable() {
				@Override
