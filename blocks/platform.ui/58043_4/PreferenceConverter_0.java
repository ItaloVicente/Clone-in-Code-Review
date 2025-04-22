	@Deprecated
	public static FontData FONTDATA_DEFAULT_DEFAULT;

	private static FontData[] fontDataArrayDefaultDefault;

	static {
		Display display = Display.getDefault();
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				FONTDATA_ARRAY_DEFAULT_DEFAULT = getFontDataArrayDefaultDefault();
				FONTDATA_DEFAULT_DEFAULT = getFontDataArrayDefaultDefault()[0];
			}
		});
	}
