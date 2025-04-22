        DialogCheck.assertDialog(dialog);
    }

    public void testUpdateConflict() {
        MessageDialog dialog = getQuestionDialog(
                getEditorString("Editor_error_save_outofsync_title"),
                getEditorString("Editor_error_save_outofsync_message"));
        DialogCheck.assertDialog(dialog);
    }
