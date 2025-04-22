					if (!event.top)
						return;
					if (combo != null) {
						if (!"about:blank".equals(event.location)) { //$NON-NLS-1$
							combo.setText(event.location);
							addToHistory(event.location);
							updateHistory();
						}// else
					}
