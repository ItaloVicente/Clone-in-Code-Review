		String[] oldPaths = event.getOldValue().toString()
				.split(Character.toString(File.pathSeparatorChar));
		String[] newPaths = event.getNewValue().toString()
				.split(Character.toString(File.pathSeparatorChar));

		List<String> removedPaths = new ArrayList<>(Arrays.asList(oldPaths));

		for (String path : newPaths) {
			removedPaths.remove(path);
		}

		for (String path : removedPaths) {
			unsetProjectSpecificFirstParentPreference(path);
		}

