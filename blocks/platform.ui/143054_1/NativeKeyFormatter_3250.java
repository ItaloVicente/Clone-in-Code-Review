	private static final String CARBON_KEY_DELIMITER_KEY = "CARBON_KEY_DELIMITER"; //$NON-NLS-1$

	private static final HashMap CARBON_KEY_LOOK_UP = new HashMap();

	private static final Comparator MODIFIER_KEY_COMPARATOR = new NativeModifierKeyComparator();

	private static final ResourceBundle RESOURCE_BUNDLE;

	private static final String WIN32_KEY_STROKE_DELIMITER_KEY = "WIN32_KEY_STROKE_DELIMITER"; //$NON-NLS-1$

	static {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(NativeKeyFormatter.class.getName());

		CARBON_KEY_LOOK_UP.put(CharacterKey.BS.toString(), "\u232B"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(CharacterKey.CR.toString(), "\u21A9"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(CharacterKey.DEL.toString(), "\u2326"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(CharacterKey.SPACE.toString(), "\u2423"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(ModifierKey.ALT.toString(), "\u2325"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(ModifierKey.COMMAND.toString(), "\u2318"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(ModifierKey.CTRL.toString(), "\u2303"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(ModifierKey.SHIFT.toString(), "\u21E7"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_DOWN.toString(), "\u2193"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_LEFT.toString(), "\u2190"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_RIGHT.toString(), "\u2192"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_UP.toString(), "\u2191"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(SpecialKey.END.toString(), "\u2198"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(SpecialKey.NUMPAD_ENTER.toString(), "\u2324"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(SpecialKey.HOME.toString(), "\u2196"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(SpecialKey.PAGE_DOWN.toString(), "\u21DF"); //$NON-NLS-1$
		CARBON_KEY_LOOK_UP.put(SpecialKey.PAGE_UP.toString(), "\u21DE"); //$NON-NLS-1$
	}

	@Override
