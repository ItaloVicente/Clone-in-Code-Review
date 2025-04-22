		String name = packName;
		if (name == null) {
			name = getPackFile().getName();
			if (name.startsWith("pack-"))
				name = name.substring("pack-".length());
			if (name.endsWith(".pack"))
				name = name.substring(0
			packName = name;
		}
