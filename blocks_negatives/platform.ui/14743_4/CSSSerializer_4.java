	public void serialize(Writer writer, CSSEngine engine, Object element,
			boolean serializeChildNodes,
			CSSSerializerConfiguration configuration) throws IOException {
		Map selectors = new HashMap();
		serialize(writer, engine, element, serializeChildNodes, selectors,
				configuration);
