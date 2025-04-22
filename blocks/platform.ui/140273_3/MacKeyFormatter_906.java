			if (ModifierKey.SHIFT.equals(modifierKey)) {
				return 0;
			}

			if (ModifierKey.CTRL.equals(modifierKey)) {
				return 1;
			}

			if (ModifierKey.ALT.equals(modifierKey)) {
				return 2;
			}

			if (ModifierKey.COMMAND.equals(modifierKey)) {
				return 3;
			}

			return Integer.MAX_VALUE;
		}
	}

	private static final HashMap KEY_LOOKUP = new HashMap();

	private static final Comparator MODIFIER_KEY_COMPARATOR = new MacModifierKeyComparator();

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(MacKeyFormatter.class.getName());

	static {
		KEY_LOOKUP.put(CharacterKey.BS.toString(), "\u232B"); //$NON-NLS-1$
		KEY_LOOKUP.put(CharacterKey.CR.toString(), "\u21A9"); //$NON-NLS-1$
		KEY_LOOKUP.put(CharacterKey.DEL.toString(), "\u2326"); //$NON-NLS-1$
		KEY_LOOKUP.put(CharacterKey.SPACE.toString(), "\u2423"); //$NON-NLS-1$
		KEY_LOOKUP.put(ModifierKey.ALT.toString(), "\u2325"); //$NON-NLS-1$
		KEY_LOOKUP.put(ModifierKey.COMMAND.toString(), "\u2318"); //$NON-NLS-1$
		KEY_LOOKUP.put(ModifierKey.CTRL.toString(), "\u2303"); //$NON-NLS-1$
		KEY_LOOKUP.put(ModifierKey.SHIFT.toString(), "\u21E7"); //$NON-NLS-1$
		KEY_LOOKUP.put(SpecialKey.ARROW_DOWN.toString(), "\u2193"); //$NON-NLS-1$
		KEY_LOOKUP.put(SpecialKey.ARROW_LEFT.toString(), "\u2190"); //$NON-NLS-1$
		KEY_LOOKUP.put(SpecialKey.ARROW_RIGHT.toString(), "\u2192"); //$NON-NLS-1$
		KEY_LOOKUP.put(SpecialKey.ARROW_UP.toString(), "\u2191"); //$NON-NLS-1$
		KEY_LOOKUP.put(SpecialKey.END.toString(), "\u2198"); //$NON-NLS-1$
		KEY_LOOKUP.put(SpecialKey.NUMPAD_ENTER.toString(), "\u2324"); //$NON-NLS-1$
		KEY_LOOKUP.put(SpecialKey.HOME.toString(), "\u2196"); //$NON-NLS-1$
		KEY_LOOKUP.put(SpecialKey.PAGE_DOWN.toString(), "\u21DF"); //$NON-NLS-1$
		KEY_LOOKUP.put(SpecialKey.PAGE_UP.toString(), "\u21DE"); //$NON-NLS-1$
	}

	@Override
