	public synchronized ResourceManager getResourceManager() {
		if (resourceManager == null) {
			Display display = PlatformUI.getWorkbench().getDisplay();
			if (display == null) {
				throw new IllegalStateException();
			}
			resourceManager = new LocalResourceManager(JFaceResources
					.getResources(display));
		}
