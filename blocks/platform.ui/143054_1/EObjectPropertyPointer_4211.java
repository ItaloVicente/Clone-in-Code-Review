		if (names == null) {
			EStructuralFeature[] pds = getPropertyDescriptors();
			names = new String[pds.length];
			for (int i = 0; i < names.length; i++) {
				names[i] = pds[i].getName();
			}
		}
		return names;
	}
