        helpSearchAction = ActionFactory.HELP_SEARCH.create(window);
        register(helpSearchAction);
		
        dynamicHelpAction = ActionFactory.DYNAMIC_HELP.create(window);
        register(dynamicHelpAction);
        
        aboutAction = ActionFactory.ABOUT.create(window);
        aboutAction
                .setImageDescriptor(IDEInternalWorkbenchImages
                        .getImageDescriptor(IDEInternalWorkbenchImages.IMG_OBJS_DEFAULT_PROD));
        register(aboutAction);
