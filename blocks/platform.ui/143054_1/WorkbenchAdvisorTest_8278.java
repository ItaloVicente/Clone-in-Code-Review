				super.initialize(c);
				c.setSaveAndRestore(true);
			}
		};

		int code2 = PlatformUI.createAndRunWorkbench(display, wa2);
		assertEquals(PlatformUI.RETURN_OK, code2);

		wa2.resetOperationIterator();
		wa2.assertNextOperation(WorkbenchAdvisorObserver.INITIALIZE);
		wa2.assertNextOperation(WorkbenchAdvisorObserver.PRE_STARTUP);
		wa2.assertNextOperation(WorkbenchAdvisorObserver.PRE_WINDOW_OPEN);
		wa2.assertNextOperation(WorkbenchAdvisorObserver.FILL_ACTION_BARS);
