			listener = detailProperty
					.adaptListener(new ISimplePropertyListener() {
						@Override
						public void handleEvent(final SimplePropertyEvent event) {
							if (!isDisposed() && !updating) {
								getRealm().exec(new Runnable() {
									@Override
									public void run() {
										if (event.type == SimplePropertyEvent.CHANGE) {
											notifyIfChanged(event.getSource());
										} else if (event.type == SimplePropertyEvent.STALE) {
											boolean wasStale = !staleKeys
													.isEmpty();
											staleKeys.add(event.getSource());
											if (!wasStale)
												fireStale();
										}
									}
								});
