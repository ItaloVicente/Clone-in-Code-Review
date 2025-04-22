        oldDesc = Workbench.getInstance().getIntroDescriptor();
        IntroDescriptor testDesc = (IntroDescriptor) WorkbenchPlugin
                .getDefault().getIntroRegistry().getIntro(
                        "org.eclipse.ui.testintro");
        Workbench.getInstance().setIntroDescriptor(testDesc);
        window = openTestWindow();
    }
