					if (!t.isDisposed()) {
						t.getDisplay().asyncExec(new Runnable() {
							@Override
							public void run() {
								if (!t.isDisposed()) {
									refresh();
								}
							}
						});
					}
