            if (filename != null && filename.length() > 0) {
                FileEditorMapping mapping = getMappingFor(filename);
                    String name;
                    String extension;
                    int index = filename.indexOf('.');
                    if (index < 0) {
                        name = filename;
                    } else {
                        name = filename.substring(0, index);
                        extension = filename.substring(index + 1);
                    }
                    mapping = new FileEditorMapping(name, extension);
                    typeEditorMappings.putDefault(mappingKeyFor(mapping),
                            mapping);
                }
                mapping.addEditor(editor);
                if (bDefault) {
