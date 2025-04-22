	private final static class ContentChangeNotifier implements IContentChangeNotifier {

			private ListenerList fListenerList;
			private final IContentChangeNotifier element;

			public ContentChangeNotifier(IContentChangeNotifier element) {
				this.element = element;
			}

			public void addContentChangeListener(IContentChangeListener listener) {
				if (fListenerList == null)
					fListenerList= new ListenerList();
				fListenerList.add(listener);
			}

			public void removeContentChangeListener(IContentChangeListener listener) {
				if (fListenerList != null) {
					fListenerList.remove(listener);
					if (fListenerList.isEmpty())
						fListenerList= null;
				}
			}

			public void fireContentChanged() {
				if (isEmpty()) {
					return;
				}
				Runnable runnable = new Runnable() {
					public void run() {
						Object[] listeners= fListenerList.getListeners();
						for (int i= 0; i < listeners.length; i++) {
							final IContentChangeListener contentChangeListener = (IContentChangeListener)listeners[i];
							SafeRunner.run(new ISafeRunnable() {
								public void run() throws Exception {
									(contentChangeListener).contentChanged(element);
								}
								public void handleException(Throwable exception) {
								}
							});
						}
					}
				};
				if (Display.getCurrent() == null) {
					Display.getDefault().syncExec(runnable);
				} else {
					runnable.run();
				}
			}

			public boolean isEmpty() {
				return fListenerList == null || fListenerList.isEmpty();
			}
	}

