                        display.syncExec(new Runnable() {
                            @Override
							public void run() {
                                if (stop || resourceNames.isDisposed()) {
									return;
								}
                                updateItem(index, itemIndex[0], itemCount[0]);
                                itemIndex[0]++;
                            }
                        });
