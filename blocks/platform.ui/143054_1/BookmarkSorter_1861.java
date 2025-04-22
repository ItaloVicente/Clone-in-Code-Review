			return result * directions[column];
		}
		case LOCATION: {
			int line1 = marker1.getAttribute(IMarker.LINE_NUMBER, -1);
			int line2 = marker2.getAttribute(IMarker.LINE_NUMBER, -1);
			int result = line1 - line2;
			if (result == 0) {
