        boolean visible = page.isEditorAreaVisible();
        if (visible) {
            page.setEditorAreaVisible(false);
            setText(WorkbenchMessages.ToggleEditor_showEditors);
        } else {
            page.setEditorAreaVisible(true);
            setText(WorkbenchMessages.ToggleEditor_hideEditors);
        }
    }
