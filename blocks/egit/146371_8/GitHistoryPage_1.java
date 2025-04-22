
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		String[] oldPaths = (oldValue != null ? oldValue.toString()
				: new String())
				.split(Character.toString(File.pathSeparatorChar));
		String[] newPaths = (newValue != null ? newValue.toString()
				: new String())
				.split(Character.toString(File.pathSeparatorChar));

		List<String> removedPaths = new ArrayList<>(Arrays.asList(oldPaths));

		for (String path : newPaths) {
			removedPaths.remove(path);
		}

		for (String path : removedPaths) {
			unsetProjectSpecificFirstParentPreference(path);
		}

