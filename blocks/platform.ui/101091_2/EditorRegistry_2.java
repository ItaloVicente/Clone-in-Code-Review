			LinkedHashSet<IEditorDescriptor> editors = new LinkedHashSet<>();
			if (contentTypeToEditorMappingsFromPlugins.containsKey(type)) {
				editors.addAll(Arrays.asList(contentTypeToEditorMappingsFromPlugins.get(type)));
			}
			if (contentTypeToEditorMappingsFromUser.containsKey(type)) {
				editors.addAll(contentTypeToEditorMappingsFromUser.get(type));
			}
			if (editors.isEmpty()) {
