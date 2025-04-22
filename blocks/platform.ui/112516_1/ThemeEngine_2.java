	private String getOsSpecificThemeId(String id, String os) {
		if (os == null) {
			return id;
		}
		return id + '_' + os;
	}

	private String getOsSpecificThemeLabel(String label, String os) {
		if (os == null) {
			return label;
		}
		return label + " - " + os;
	}

