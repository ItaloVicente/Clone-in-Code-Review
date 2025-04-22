				}
			}

			i++;
		}

		try {
			return new KeyStroke(modifierKeys, naturalKey);
		} catch (Throwable t) {
			throw new ParseException("Cannot create key stroke with " //$NON-NLS-1$
					+ modifierKeys + " and " + naturalKey); //$NON-NLS-1$
		}
	}

	private transient int hashCode;

	private transient boolean hashCodeComputed;

	private SortedSet modifierKeys;

	private transient ModifierKey[] modifierKeysAsArray;

	private NaturalKey naturalKey;

	private KeyStroke(SortedSet modifierKeys, NaturalKey naturalKey) {
		this.modifierKeys = Util.safeCopy(modifierKeys, ModifierKey.class);
		this.naturalKey = naturalKey;
		this.modifierKeysAsArray = (ModifierKey[]) this.modifierKeys.toArray(new ModifierKey[this.modifierKeys.size()]);
	}

	@Override
