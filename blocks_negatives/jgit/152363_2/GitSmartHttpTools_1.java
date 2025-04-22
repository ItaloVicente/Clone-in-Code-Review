	private static boolean isUploadPackSideBand(HttpServletRequest req) {
		try {
			String line = new PacketLineIn(req.getInputStream()).readString();
			FirstWant parsed = FirstWant.fromLine(line);
			return (parsed.getCapabilities().contains(OPTION_SIDE_BAND)
					|| parsed.getCapabilities().contains(OPTION_SIDE_BAND_64K));
		} catch (IOException e) {
			return false;
		}
	}

