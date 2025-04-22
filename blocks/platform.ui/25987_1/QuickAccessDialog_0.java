
									final QuickAccessElement element = (QuickAccessElement) selectedElement;
									Display.getDefault().asyncExec(new Runnable() {
										@Override
										public void run() {
											element.execute();
										}
									});
