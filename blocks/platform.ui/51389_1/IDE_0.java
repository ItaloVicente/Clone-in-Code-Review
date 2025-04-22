		return removeNullEntries(editorDescriptors);
	}

	private static IEditorDescriptor[] removeNullEntries(IEditorDescriptor[] editorDescriptors) {
		boolean nullDescriptorFound = false;
		for (IEditorDescriptor d : editorDescriptors) {
			if (d == null) {
				nullDescriptorFound = true;
				break;
			}
		}
		if (!nullDescriptorFound) {
			return editorDescriptors;
		}
		List<IEditorDescriptor> nonNullDescriptors = new ArrayList<>(editorDescriptors.length);
		for (IEditorDescriptor d : editorDescriptors) {
			if (d != null) {
				nonNullDescriptors.add(d);
			}
		}
		return nonNullDescriptors.toArray(new IEditorDescriptor[nonNullDescriptors.size()]);
