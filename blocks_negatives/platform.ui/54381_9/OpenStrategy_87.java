                    display.asyncExec(new Runnable() {
                        @Override
						public void run() {
                            if (arrowKeyDown) {
                                display.timerExec(TIME, new Runnable() {

                                    @Override
									public void run() {
                                        if (id == count[0]) {
                                            firePostSelectionEvent(new SelectionEvent(
                                                    e));
                                            if ((CURRENT_METHOD & ARROW_KEYS_OPEN) != 0) {
												fireOpenEvent(new SelectionEvent(
                                                        e));
											}
                                        }
                                    }
                                });
                            } else {
                                firePostSelectionEvent(new SelectionEvent(e));
                            }
                        }
                    });
