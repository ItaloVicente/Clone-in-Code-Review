		if (string == null) {
			final StringBuilder stringBuffer = new StringBuilder();
			stringBuffer.append('[');
			stringBuffer.append(activityIds);
			stringBuffer.append(',');
			stringBuffer.append(enabled);
			stringBuffer.append(',');
			stringBuffer.append(id);
			stringBuffer.append(']');
			string = stringBuffer.toString();
		}

		return string;
	}
