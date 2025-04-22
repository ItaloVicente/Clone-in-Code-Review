            if (fileExtension != null && fileExtension.length() > 0) {
                    mapping = new FileEditorMapping(fileExtension);
                    typeEditorMappings.putDefault(mappingKeyFor(mapping),
                            mapping);
                }
                mapping.addEditor(editor);
                if (bDefault) {
