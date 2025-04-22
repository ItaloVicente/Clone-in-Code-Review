		if (message.indexOf(CHANGE_ID) > 0) {
			if (replaceExisting) {
				int i = message.indexOf(CHANGE_ID) + 10;
				while (message.charAt(i) == ' ')
					i++;
				String oldId = message.length() == (i + 40) ?
						message.substring(i) : message.substring(i, i + 41);
				message = message.replace(oldId, "I" + changeId.getName()); //$NON-NLS-1$
