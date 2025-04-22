        hideShowEditorAction = ActionFactory.SHOW_EDITOR.create(window);
        register(hideShowEditorAction);
        editActionSetAction = ActionFactory.EDIT_ACTION_SETS
                .create(window);
        register(editActionSetAction);
        lockToolBarAction = ActionFactory.LOCK_TOOL_BAR.create(window);
        register(lockToolBarAction);
        closePerspAction = ActionFactory.CLOSE_PERSPECTIVE.create(window);
        register(closePerspAction);
        closeAllPerspsAction = ActionFactory.CLOSE_ALL_PERSPECTIVES
                .create(window);
        register(closeAllPerspsAction);
