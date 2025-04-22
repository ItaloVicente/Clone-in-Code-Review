		for (String option : capabilities) {
			if (option.startsWith(CAPABILITY_SYMREF_PREFIX)) {
				String[] symRef = option
						.substring(CAPABILITY_SYMREF_PREFIX.length())
						.split(":", 2); //$NON-NLS-1$
				if (symRef.length == 2) {
					symRefs.put(symRef[0], symRef[1]);
