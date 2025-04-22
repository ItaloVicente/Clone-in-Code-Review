			IWorkbenchPage page = PlatformUI.getWorkbench().showPerspective(ORG_ECLIPSE_JDT_UI_JAVA_PERSPECTIVE,
					workbenchWindow);
			for (index = 0; index < numIterations; index++) {
				consoleView = page.showView("org.eclipse.ui.views.ResourceNavigator");
				page.hideView(consoleView);
			}
