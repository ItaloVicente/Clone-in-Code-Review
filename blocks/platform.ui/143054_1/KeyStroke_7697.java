		return new KeyStroke(new TreeSet(Collections.singletonList(modifierKey)), naturalKey);
	}

	public static KeyStroke getInstance(ModifierKey[] modifierKeys, NaturalKey naturalKey) {
		Util.assertInstance(modifierKeys, ModifierKey.class);
		return new KeyStroke(new TreeSet(Arrays.asList(modifierKeys)), naturalKey);
	}

	public static KeyStroke getInstance(NaturalKey naturalKey) {
		return new KeyStroke(Util.EMPTY_SORTED_SET, naturalKey);
	}

	public static KeyStroke getInstance(SortedSet modifierKeys, NaturalKey naturalKey) {
		return new KeyStroke(modifierKeys, naturalKey);
	}

	public static KeyStroke getInstance(String string) throws ParseException {
		if (string == null) {
