	private final static class ContentChangeNotifier implements IContentChangeNotifier {

			private ListenerList fListenerList;
			private final IContentChangeNotifier element;

			public ContentChangeNotifier(IContentChangeNotifier element) {
				this.element = element;
			}

			/* (non-Javadoc)
			 * see IContentChangeNotifier.addChangeListener
			 */
			@Override
			public void addContentChangeListener(IContentChangeListener listener) {
				if (fListenerList == null)
					fListenerList= new ListenerList();
				fListenerList.add(listener);
			}

			/* (non-Javadoc)
			 * see IContentChangeNotifier.removeChangeListener
			 */
			@Override
			public void removeContentChangeListener(IContentChangeListener listener) {
				if (fListenerList != null) {
					fListenerList.remove(listener);
					if (fListenerList.isEmpty())
						fListenerList= null;
				}
			}

			/**
			 * Notifies all registered <code>IContentChangeListener</code>s of a content change.
			 */
			public void fireContentChanged() {
				if (isEmpty()) {
					return;
				}
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						Object[] listeners= fListenerList.getListeners();
						for (int i= 0; i < listeners.length; i++) {
							final IContentChangeListener contentChangeListener = (IContentChangeListener)listeners[i];
						SafeRunnable.run(() -> (contentChangeListener)
								.contentChanged(element));
						}
					}
				};
				if (Display.getCurrent() == null) {
					Display.getDefault().syncExec(runnable);
				} else {
					runnable.run();
				}
			}

			/**
			 * Return whether this notifier is empty (i.e. has no listeners).
			 * @return whether this notifier is empty
			 */
			public boolean isEmpty() {
				return fListenerList == null || fListenerList.isEmpty();
			}
	}

