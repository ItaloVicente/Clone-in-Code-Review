	public void testPerspectiveBarExtrasGetOpened() {
		WorkbenchAdvisor wa = new WorkbenchAdvisorObserver(1) {

			public void preStartup() {
				super.preStartup();
				PrefUtil.getAPIPreferenceStore().setValue(IWorkbenchPreferenceConstants.PERSPECTIVE_BAR_EXTRAS,
						"org.eclipse.debug.ui.DebugPerspective,org.eclipse.jdt.ui.JavaBrowsingPerspective");
			}

			public void postStartup() {
				super.postStartup();
				IWorkbenchPage activePage = getWorkbenchConfigurer().getWorkbench().getActiveWorkbenchWindow()
						.getActivePage();
				IPerspectiveDescriptor[] openPerspectives = activePage.getOpenPerspectives();
				assertEquals(3, openPerspectives.length);
				assertEquals(openPerspectives[1].getId(), "org.eclipse.debug.ui.DebugPerspective");
				assertEquals(openPerspectives[2].getId(), "org.eclipse.jdt.ui.JavaBrowsingPerspective");
			}

		};

		int code = PlatformUI.createAndRunWorkbench(display, wa);
		assertEquals(PlatformUI.RETURN_OK, code);
	}

