            dialog = new MessageDialog(
                    getShell(),
                    bundle
                            .getString("Editor_error_activated_deleted_save_title"),
                    null,
                    bundle
                            .getString("Editor_error_activated_deleted_save_message"),
                    MessageDialog.QUESTION,
                    new String[] {
                            bundle
                                    .getString("Editor_error_activated_deleted_save_button_save"),
                            bundle
                                    .getString("Editor_error_activated_deleted_save_button_close") },
                    0);
