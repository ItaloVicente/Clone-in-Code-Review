				display.addListener(SWT.Dispose,
						createOnDisplayDisposedListener());
			}
		}

		protected Listener createOnDisplayDisposedListener() {
			return new Listener() {
