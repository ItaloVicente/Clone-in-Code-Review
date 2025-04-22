	private static class CTabFolderRendererWrapper extends ReflectionSupport<CTabFolderRenderer> {
		private Method drawBackgroundMethod;

		public CTabFolderRendererWrapper(CTabFolderRenderer instance) {
			super(instance);
		}

		public void drawBackground(GC gc, int x, int y, int width, int height, Color defaultBackground, Color[] colors,
				int[] percents, boolean vertical) {
			if (drawBackgroundMethod == null) {
				drawBackgroundMethod = getMethod("drawBackground", //$NON-NLS-1$
						GC.class, int[].class, int.class, int.class, int.class, int.class, Color.class, Image.class,
						Color[].class, int[].class, boolean.class);
