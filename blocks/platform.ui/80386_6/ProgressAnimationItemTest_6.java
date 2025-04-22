		Display display = PlatformUI.getWorkbench().getDisplay();
		display.asyncExec(() -> {
			try {
				assertEquals(1, getAccessibleListenersSize(getToolBar(animationItem).getAccessible()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		while (display.readAndDispatch()) {
		}
