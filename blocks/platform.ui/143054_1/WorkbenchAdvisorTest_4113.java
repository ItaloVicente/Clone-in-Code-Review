				super.postWindowClose(c);
			}
		};

		int code = PlatformUI.createAndRunWorkbench(display, wa);
		assertEquals(PlatformUI.RETURN_OK, code);

		wa.resetOperationIterator();
		wa.assertNextOperation(WorkbenchAdvisorObserver.INITIALIZE);
		wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_STARTUP);
		wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_WINDOW_OPEN);
		wa.assertNextOperation(WorkbenchAdvisorObserver.FILL_ACTION_BARS);
		wa.assertNextOperation(WorkbenchAdvisorObserver.POST_WINDOW_OPEN);
		wa.assertNextOperation(WorkbenchAdvisorObserver.POST_STARTUP);
		wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_SHUTDOWN);
		wa.assertNextOperation(WorkbenchAdvisorObserver.POST_SHUTDOWN);
		wa.assertAllOperationsExamined();
	}
