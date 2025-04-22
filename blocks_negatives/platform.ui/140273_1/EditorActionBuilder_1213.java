        public void editorChanged(IEditorPart editor) {
            if (actions != null) {
                for (int i = 0; i < actions.size(); i++) {
                    ActionDescriptor ad = (ActionDescriptor) actions.get(i);
                    EditorPluginAction action = (EditorPluginAction) ad
                            .getAction();
                    action.editorChanged(editor);
                }
            }
        }
    }
