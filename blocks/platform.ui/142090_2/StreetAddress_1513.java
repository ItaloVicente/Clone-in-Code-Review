		if (property.equals(P_ID_BUILD_NO)) {
			setBuildNo(BUILD_NO_DEFAULT);
			return;
		}
		if (property.equals(P_ID_APTBOX)) {
			setAptBox(APTBOX_DEFAULT);
			return;
		}
		if (property.equals(P_ID_STREET)) {
			setStreetName(STREETNAME_DEFAULT);
			return;
		}
	}

	private void setAptBox(String newAptBox) {
		aptBox = newAptBox;
	}

	private void setBuildNo(Integer newBuildNo) {
		buildNo = newBuildNo;
	}

	@Override
