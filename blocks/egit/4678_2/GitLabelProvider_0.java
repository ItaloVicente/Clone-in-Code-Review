	public static String formatBranchTrackingStatus(BranchTrackingStatus status) {
		StringBuilder sb = new StringBuilder();
		int ahead = status.getAheadCount();
		int behind = status.getBehindCount();
		if (ahead != 0) {
			sb.append('↑');
			sb.append(ahead);
		}
		if (behind != 0) {
			if (sb.length() != 0)
				sb.append(' ');
			sb.append('↓');
			sb.append(status.getBehindCount());
		}
		return sb.toString();
	}

