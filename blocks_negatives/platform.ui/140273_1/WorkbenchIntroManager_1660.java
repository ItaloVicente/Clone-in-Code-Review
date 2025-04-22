        try {
            workbenchPage.showView(IIntroConstants.INTRO_VIEW_ID);
        } catch (PartInitException e) {
            WorkbenchPlugin
                    .log(
                            IntroMessages.Intro_could_not_create_part, new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, IStatus.ERROR, IntroMessages.Intro_could_not_create_part, e));
        }
    }

    @Override
