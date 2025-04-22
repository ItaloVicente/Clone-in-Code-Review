			String nameString = memento	.getString(MarkerSupportInternalUtilities.ATTRIBUTE_NAME);
			if (nameString != null && nameString.length() > 0) {
				name = nameString;
			} else {
				name = idString;
			}
