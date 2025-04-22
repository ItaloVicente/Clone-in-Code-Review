		return keyStrokes.equals(((KeySequence) object).keyStrokes);
	}

	public String format() {
		return KeyFormatterFactory.getDefault().format(this);
	}

	public List getKeyStrokes() {
		return keyStrokes;
	}

	@Override
