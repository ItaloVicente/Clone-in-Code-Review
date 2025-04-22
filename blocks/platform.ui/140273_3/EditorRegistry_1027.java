			if (fileExtension != null && fileExtension.length() > 0) {
				FileEditorMapping mapping = getMappingFor("*." + fileExtension); //$NON-NLS-1$
				if (mapping == null) { // no mapping for that extension
					mapping = new FileEditorMapping(fileExtension);
					typeEditorMappings.putDefault(mappingKeyFor(mapping), mapping);
				}
				mapping.addEditor(editor);
				if (bDefault) {
