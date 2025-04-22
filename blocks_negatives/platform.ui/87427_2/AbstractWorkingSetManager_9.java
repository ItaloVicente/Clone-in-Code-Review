		Runnable notifier = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < listeners.length; i++) {
					final IPropertyChangeListener listener = (IPropertyChangeListener) listeners[i];
					ISafeRunnable safetyWrapper = new ISafeRunnable() {

						@Override
						public void run() throws Exception {
							listener.propertyChange(event);
						}
