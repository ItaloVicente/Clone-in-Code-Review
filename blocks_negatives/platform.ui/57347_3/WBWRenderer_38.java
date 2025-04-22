					new IWindowCloseHandler() {
						@Override
						public boolean close(MWindow window) {
							return closeDetachedWindow(window);
						}
					});
