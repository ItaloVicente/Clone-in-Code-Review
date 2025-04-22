	private List<String> getV2CapabilityAdvertisement() {
		ArrayList<String> caps = new ArrayList<>();
		caps.add(COMMAND_LS_REFS);
		caps.add(
				COMMAND_FETCH + '=' +
				OPTION_SHALLOW);
		return caps;
	}

