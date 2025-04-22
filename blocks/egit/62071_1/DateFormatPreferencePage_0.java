		int offset = (localTimeZone.getRawOffset() / 3600 / 1000 - 6);
		if (offset < -12) {
			offset += 24;
		}
		String[] zoneIds = TimeZone.getAvailableIDs(offset * 3600 * 1000);
		if (zoneIds.length == 0) {
			return localTimeZone;
		}
		return TimeZone.getTimeZone(zoneIds[0]);
