				try {
					IWorkbenchWindow window = getWorkbenchConfigurer()
							.getWorkbench().getActiveWorkbenchWindow();
					IWorkbenchPage page = window.getActivePage();
					page.showView(EmptyView.ID);
					assertNotNull(page.findView(EmptyView.ID));
					page.resetPerspective();
					assertNull(page.findView(EmptyView.ID));
				} catch (PartInitException e) {
					fail(e.toString());
				}
			}
		};
		int code = PlatformUI.createAndRunWorkbench(display, wa);
		assertEquals(PlatformUI.RETURN_OK, code);
	}
