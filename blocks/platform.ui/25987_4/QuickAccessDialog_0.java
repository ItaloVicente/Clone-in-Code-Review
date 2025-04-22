
									final QuickAccessElement element = (QuickAccessElement) selectedElement;
									window.getShell().getDisplay().asyncExec(new Runnable() {
										@Override
										public void run() {
											element.execute();
										}
									});
