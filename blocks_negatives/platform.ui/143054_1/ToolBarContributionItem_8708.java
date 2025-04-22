        if (checkDisposed()) {
            return;
        }

        if (coolItem == null && coolBar != null) {
            ToolBar oldToolBar = toolBarManager.getControl();
            ToolBar toolBar = toolBarManager.createControl(coolBar);
            if ((oldToolBar != null) && (oldToolBar.equals(toolBar))) {
                toolBarManager.update(true);
            }

            if (toolBar.getItemCount() < 1) {
