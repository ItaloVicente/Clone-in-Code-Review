		super.doSetUp();

		IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
		preferenceStore.putValue(IWorkbenchPreferenceConstants.ENABLE_32_STICKY_CLOSE_BEHAVIOR, "true");

		oldDesc = Workbench.getInstance().getIntroDescriptor();
		IntroDescriptor testDesc = (IntroDescriptor) WorkbenchPlugin
				.getDefault().getIntroRegistry().getIntro(
						"org.eclipse.ui.testintro");
		Workbench.getInstance().setIntroDescriptor(testDesc);
		window = openTestWindow();
	}
