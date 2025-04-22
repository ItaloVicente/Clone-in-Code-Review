							display.timerExec(TIME, () -> {
								if (id == count[0]) {
									firePostSelectionEvent(new SelectionEvent(
											e));
									if ((CURRENT_METHOD & ARROW_KEYS_OPEN) != 0) {
										fireOpenEvent(new SelectionEvent(
												e));
									}
								}
							});
