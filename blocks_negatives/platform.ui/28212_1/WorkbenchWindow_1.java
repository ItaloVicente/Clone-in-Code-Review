				return tracker;
			}
		});

		windowContext.set(IWindowCloseHandler.class.getName(), new IWindowCloseHandler() {
			public boolean close(MWindow window) {
				return getWindowAdvisor().preWindowShellClose() && WorkbenchWindow.this.close();
			}
		});
