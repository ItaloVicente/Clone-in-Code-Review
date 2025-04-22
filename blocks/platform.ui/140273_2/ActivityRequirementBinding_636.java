		if (string == null) {
			final StringBuilder stringBuffer = new StringBuilder();
			stringBuffer.append('[');
			stringBuffer.append(requiredActivityId);
			stringBuffer.append(',');
			stringBuffer.append(activityId);
			stringBuffer.append(']');
			string = stringBuffer.toString();
		}

		return string;
	}
