		StringBuilder stringBuffer = new StringBuilder();

		List keyStrokes = keySequence.getKeyStrokes();
		KeyStroke[] keyStrokeArray = (KeyStroke[]) keyStrokes.toArray(new KeyStroke[keyStrokes.size()]);
		Set previousModifierKeys = Collections.EMPTY_SET;
		List naturalKeys = new ArrayList();
		for (int i = 0; i < keyStrokeArray.length; i++) {
			KeyStroke keyStroke = keyStrokeArray[i];
			Set currentModifierKeys = keyStroke.getModifierKeys();

			if (!previousModifierKeys.equals(currentModifierKeys)) {
				if (i > 0) {
					stringBuffer.append(formatKeyStrokes(previousModifierKeys, naturalKeys));
					stringBuffer.append(getKeyStrokeDelimiter());
				}

				previousModifierKeys = currentModifierKeys;
				naturalKeys.clear();

			}

			naturalKeys.add(keyStroke.getNaturalKey());
		}

		stringBuffer.append(formatKeyStrokes(previousModifierKeys, naturalKeys));

		return stringBuffer.toString();
	}

	public String formatKeyStrokes(Set modifierKeys, List naturalKeys) {
		StringBuilder stringBuffer = new StringBuilder();
		String keyDelimiter = getKeyDelimiter();

		SortedSet sortedModifierKeys = new TreeSet(getModifierKeyComparator());
		sortedModifierKeys.addAll(modifierKeys);
		Iterator sortedModifierKeyItr = sortedModifierKeys.iterator();
		while (sortedModifierKeyItr.hasNext()) {
			stringBuffer.append(format((ModifierKey) sortedModifierKeyItr.next()));
			stringBuffer.append(keyDelimiter);
		}

		Iterator naturalKeyItr = naturalKeys.iterator();
		while (naturalKeyItr.hasNext()) {
			Object naturalKey = naturalKeyItr.next();
			if (naturalKey instanceof NaturalKey) {
				stringBuffer.append(format((NaturalKey) naturalKey));
				if (naturalKeyItr.hasNext()) {
					stringBuffer.append(keyDelimiter);
				}
			}
		}

		return stringBuffer.toString();

	}
