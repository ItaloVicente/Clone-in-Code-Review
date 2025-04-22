	public CSSStyleDeclaration parseStyleDeclaration(InputSource source)
			throws IOException {
		CSSStyleDeclarationImpl styleDeclaration = new CSSStyleDeclarationImpl(
				null);
		parseStyleDeclaration(((styleDeclaration)),
				source);
