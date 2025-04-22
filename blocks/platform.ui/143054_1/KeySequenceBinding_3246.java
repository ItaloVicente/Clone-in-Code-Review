		if (string == null) {
			final StringBuilder stringBuffer = new StringBuilder();
			stringBuffer.append('[');
			stringBuffer.append(keySequence);
			stringBuffer.append(',');
			stringBuffer.append(match);
			stringBuffer.append(']');
			string = stringBuffer.toString();
		}

		return string;
	}
