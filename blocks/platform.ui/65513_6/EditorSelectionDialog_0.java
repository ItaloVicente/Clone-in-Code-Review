			associateEditor = true;
		}
		if (associateEditor) {
			List<IFileEditorMapping> newMappings = new ArrayList<>();
			newMappings.addAll(Arrays.asList(reg.getFileEditorMappings()));
			reg.setFileEditorMappings(newMappings.toArray(new FileEditorMapping[newMappings.size()]));
			reg.saveAssociations();
