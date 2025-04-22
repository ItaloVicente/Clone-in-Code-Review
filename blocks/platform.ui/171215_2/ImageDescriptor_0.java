	public static SymbolImageDescriptor createFromSymbol(String symbol, ColorDescriptor colorDescriptor) {
		return new SymbolImageDescriptor(symbol, colorDescriptor, null, 16);
	}


	public static SymbolImageDescriptor createFromSymbol(String symbol, ColorDescriptor colorDescriptor,
			FontDescriptor fontDescriptor, int initialSize) {
		return new SymbolImageDescriptor(symbol, colorDescriptor, fontDescriptor, initialSize);
	}

