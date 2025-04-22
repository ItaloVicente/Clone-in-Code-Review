		return new ListeningValue<IWorkbenchPage>() {
			private final IPageListener listener = new IPageListener() {
				@Override
				public void pageActivated(IWorkbenchPage page) {
					protectedSetValue(page);
				}

				@Override
				public void pageClosed(IWorkbenchPage page) {
					if (page == doGetValue()) {
						protectedSetValue(null);
					}
				}

				@Override
				public void pageOpened(IWorkbenchPage page) {
				}
			};

			@Override
			protected void startListening() {
				window.addPageListener(listener);
			}

			@Override
			protected void stopListening() {
				window.removePageListener(listener);
			}

			@Override
			protected IWorkbenchPage calculate() {
				return window.getActivePage();
			}
		};
