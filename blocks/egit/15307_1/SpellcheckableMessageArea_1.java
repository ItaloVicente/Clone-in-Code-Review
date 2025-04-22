					IPreferenceStore store = getStore();
					whitespaceCharPainter = new WhitespaceCharacterPainter(
							v,
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_LEADING_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_TRAILING_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_LEADING_IDEOGRAPHIC_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_IDEOGRAPHIC_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_TRAILING_IDEOGRAPHIC_SPACES),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_LEADING_TABS),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_ENCLOSED_TABS),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_TRAILING_TABS),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_CARRIAGE_RETURN),
							store.getBoolean(AbstractTextEditor.PREFERENCE_SHOW_LINE_FEED),
							store.getInt(AbstractTextEditor.PREFERENCE_WHITESPACE_CHARACTER_ALPHA_VALUE));
