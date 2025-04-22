		if (string == null) {
			final StringBuilder stringBuffer = new StringBuilder();
			stringBuffer.append('[');
			stringBuffer.append(id);
			stringBuffer.append(',');
			stringBuffer.append(name);
			stringBuffer.append(',');
			stringBuffer.append(sourceId);
			stringBuffer.append(']');
			string = stringBuffer.toString();
		}

		return string;
	}

	public String getDescription() {
		return description;
	}
