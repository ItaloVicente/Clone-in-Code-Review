		if (name.equals(P_ID_BUILD_NO)) {
			try {
				setBuildNo(Integer.valueOf(Integer.parseInt((String) value)));
			} catch (NumberFormatException e) {
				setBuildNo(BUILD_NO_DEFAULT);
			}
			return;
		}
		if (name.equals(P_ID_APTBOX)) {
			setAptBox((String) value);
			return;
		}
		if (name.equals(P_ID_STREET)) {
			setStreetName((String) value);
			return;
		}
	}

	private void setStreetName(String newStreetName) {
		streetName = newStreetName;
	}

	@Override
