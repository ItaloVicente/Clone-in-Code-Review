	private void recordFileMode(String path
		Set<String> values = fileModes.get(mode);
		if (path != null) {
			if (values == null) {
				values = new HashSet<>();
				fileModes.put(mode
			}
			values.add(path);
		}
	}

