		String name = packName;
		if (name == null) {
			name = getPackFile().getName();
				name = name.substring(0, name.length() - ".pack".length()); //$NON-NLS-1$
			packName = name;
		}
		return name;
