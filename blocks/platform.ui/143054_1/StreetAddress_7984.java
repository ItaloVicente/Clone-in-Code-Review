		return toString().equals(ob.toString());
	}

	private String getAptBox() {
		if (aptBox == null)
			aptBox = APTBOX_DEFAULT;
		return aptBox;
	}

	private Integer getBuildNo() {
		if (buildNo == null)
			buildNo = BUILD_NO_DEFAULT;
		return buildNo;
	}

	private static ArrayList<TextPropertyDescriptor> getDescriptors() {
		return descriptors;
	}

	@Override
