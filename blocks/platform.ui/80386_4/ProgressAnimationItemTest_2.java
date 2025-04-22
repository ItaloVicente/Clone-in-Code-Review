		PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
			try {
				assertEquals(1, getAccessibleListenersSize(getToolBar(animationItem).getAccessible()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		Display display = Display.getCurrent();
		while (display.readAndDispatch()) {
		}
