			windowContext.set(IWindowCloseHandler.class.getName(), new IWindowCloseHandler() {
				@Override
				public boolean close(MWindow window) {
					return getWindowAdvisor().preWindowShellClose() && WorkbenchWindow.this.close();
				}
			});
