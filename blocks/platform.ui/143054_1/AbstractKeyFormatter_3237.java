		String keyDelimiter = getKeyDelimiter();

		SortedSet modifierKeys = new TreeSet(getModifierKeyComparator());
		modifierKeys.addAll(keyStroke.getModifierKeys());
		StringBuilder stringBuffer = new StringBuilder();
		Iterator modifierKeyItr = modifierKeys.iterator();
		while (modifierKeyItr.hasNext()) {
			stringBuffer.append(format((ModifierKey) modifierKeyItr.next()));
			stringBuffer.append(keyDelimiter);
		}

		NaturalKey naturalKey = keyStroke.getNaturalKey();
		if (naturalKey != null) {
			stringBuffer.append(format(naturalKey));
		}

		return stringBuffer.toString();

	}

	protected abstract String getKeyDelimiter();

	protected abstract String getKeyStrokeDelimiter();

	protected abstract Comparator getModifierKeyComparator();
