			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (isDisposed()) {
						return;
					}
					outsideListener.handleLifecycleEvent(new SaveablesLifecycleEvent(
							saveablesSource, SaveablesLifecycleEvent.POST_OPEN,
							addedSaveables
							.toArray(new Saveable[addedSaveables.size()]),
							false));
