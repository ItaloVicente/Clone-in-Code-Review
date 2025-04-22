        
        switchToEditorAction = ActionFactory.SHOW_OPEN_EDITORS
                .create(window);
        register(switchToEditorAction);

        workbookEditorsAction = ActionFactory.SHOW_WORKBOOK_EDITORS
        		.create(window);
        register(workbookEditorsAction);
        
        quickAccessAction = ActionFactory.SHOW_QUICK_ACCESS
        	.create(window);
