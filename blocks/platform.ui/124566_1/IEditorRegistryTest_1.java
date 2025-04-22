
		List<EditorDescriptor> list = Arrays.asList(sortedEditorsFromOS).stream().map(x -> (EditorDescriptor) x)
				.collect(Collectors.toList());
		Map<String, List<Program>> map = list.stream().collect(Collectors.groupingBy(IEditorDescriptor::getId,
				Collectors.mapping(EditorDescriptor::getProgram, Collectors.toList())));

		assertTrue(!map.isEmpty());
