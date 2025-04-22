								IWorkbenchWindow window = getActiveWindow(app);
								if (window != null) {
									ISelectionService service = window.getSelectionService();
									IActionDelegate delegate = handlerProxy.getDelegate();
									delegate.selectionChanged(handlerProxy.getAction(), service.getSelection());
								}
