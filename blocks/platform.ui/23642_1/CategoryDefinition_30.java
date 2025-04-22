		}

		return hashCode;
	}

	public String toString() {
		if (string == null) {
			final StringBuffer stringBuffer = new StringBuffer();
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
