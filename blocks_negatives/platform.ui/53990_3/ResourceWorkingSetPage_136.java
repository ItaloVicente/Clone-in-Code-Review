                            new Runnable() {
                                @Override
								public void run() {
                                    setSubtreeChecked((IContainer) element,
                                            tree.getChecked(element), false);
                                }
                            });
