		synchronized (this) {
			if (resourceManager == null) {
				Display display = Display.getCurrent();
				if (display == null && PlatformUI.isWorkbenchRunning()) {
					display = PlatformUI.getWorkbench().getDisplay();
				}
				if (display == null) {
					throw new SWTError(SWT.ERROR_THREAD_INVALID_ACCESS);
				}
				resourceManager = new LocalResourceManager(
						JFaceResources.getResources(display));
			}
		}
