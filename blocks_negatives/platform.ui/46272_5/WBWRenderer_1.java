	private void setCloseHandler(MWindow window) {
		IEclipseContext context = window.getContext();
		if (window.getParent() == null) {
			context.set(IWindowCloseHandler.class,
					new IWindowCloseHandler() {
						@Override
						public boolean close(MWindow window) {
							return closeDetachedWindow(window);
						}
					});
		} else {
			context.set(IWindowCloseHandler.class,
					new IWindowCloseHandler() {
						@Override
						public boolean close(MWindow window) {
							EPartService partService = window.getContext().get(EPartService.class);
							return partService.saveAll(true);
						}
					});
		}
