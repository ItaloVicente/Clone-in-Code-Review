    
	private static FontData[] readDefaultFontData() {
		final Display display = Display.getCurrent() == null ? Display.getDefault() : Display.getCurrent();

		final AtomicReference<FontData[]> result = new AtomicReference<FontData[]>();

		display.syncExec(new Runnable() {

			public void run() {
				result.set(display.getSystemFont().getFontData());
			}
		});

		return result.get();
	}
