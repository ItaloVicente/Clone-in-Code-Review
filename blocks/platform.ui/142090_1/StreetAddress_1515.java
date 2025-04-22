		StringBuilder outStringBuilder = new StringBuilder();
		if (!getAptBox().equals(APTBOX_DEFAULT)) {
			outStringBuilder.append(getAptBox());
			outStringBuilder.append(", "); //$NON-NLS-1$
		}
		if (!getBuildNo().equals(BUILD_NO_DEFAULT)) {
			outStringBuilder.append(getBuildNo());
			outStringBuilder.append(" "); //$NON-NLS-1$
		}
		if (!getStreetName().equals(STREETNAME_DEFAULT)) {
			outStringBuilder.append(getStreetName());
		}
		return outStringBuilder.toString();
	}
