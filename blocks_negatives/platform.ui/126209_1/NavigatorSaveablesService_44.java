			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (isDisposed()) {
						return;
					}
					outsideListener
							.handleLifecycleEvent(new SaveablesLifecycleEvent(
									saveablesSource,
									SaveablesLifecycleEvent.PRE_CLOSE,
									removedSaveables
											.toArray(new Saveable[removedSaveables
													.size()]), true));
					outsideListener
							.handleLifecycleEvent(new SaveablesLifecycleEvent(
									saveablesSource,
									SaveablesLifecycleEvent.POST_CLOSE,
									removedSaveables
											.toArray(new Saveable[removedSaveables
													.size()]), false));
