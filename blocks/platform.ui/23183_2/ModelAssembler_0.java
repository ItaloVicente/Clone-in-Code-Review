		runProcessors(extensions, false);
		processFragments(extensions, imports, addedElements);
		runProcessors(extensions, true);

		resolveImports(imports, addedElements);
	}

	private void processFragments(IExtension[] extensions, List<MApplicationElement> imports,
			List<MApplicationElement> addedElements) {
