		wa2.assertNextOperation(WorkbenchAdvisorObserver.POST_WINDOW_OPEN);
		wa2.assertNextOperation(WorkbenchAdvisorObserver.POST_STARTUP);
		wa2.assertNextOperation(WorkbenchAdvisorObserver.PRE_SHUTDOWN);
		wa2.assertNextOperation(WorkbenchAdvisorObserver.POST_SHUTDOWN);
		wa2.assertAllOperationsExamined();
	}

