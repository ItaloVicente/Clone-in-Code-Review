			FileEditorMapping mapping = getMappingFor(fileName);
			if (mapping != null) {
				related = mapping.getDeclaredDefaultEditors();
				for (int i = 0; i < related.length; i++) {
					if (!allRelated.contains(related[i])) {
						if (!WorkbenchActivityHelper.filterItem(related[i])) {
							allRelated.add(related[i]);
						}
					}
				}
				
				nonDefaultFileEditors.addAll(Arrays.asList(mapping.getEditors()));
			}
			
			int index = fileName.lastIndexOf('.');
			if (index > -1) {
				mapping = getMappingFor(extension);
