        while (extEditors.hasMoreTokens()) {
            String extEditor = extEditors.nextToken().trim();
            int index = extEditor.indexOf(':');
            if (extEditor.length() < 3 || index <= 0
                    || index >= (extEditor.length() - 1)) {
                WorkbenchPlugin
                return;
            }
            String ext = extEditor.substring(0, index).trim();
            String editorId = extEditor.substring(index + 1).trim();
            FileEditorMapping mapping = getMappingFor(ext);
            if (mapping == null) {
                WorkbenchPlugin
                continue;
            }
