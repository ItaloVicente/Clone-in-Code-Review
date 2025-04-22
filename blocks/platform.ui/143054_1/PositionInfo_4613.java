	public static PositionInfo parse(String positionInfo) {
		Position position = Position.find(positionInfo);
		if (position != null) {
			switch (position) {
				case FIRST:
					return PositionInfo.FIRST;
