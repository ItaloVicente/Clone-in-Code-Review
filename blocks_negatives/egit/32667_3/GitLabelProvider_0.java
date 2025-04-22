	/**
	 * Format the branch tracking status suitable for displaying in decorations and labels.
	 *
	 * @param status
	 * @return the branch tracking status as a string
	 */
	public static String formatBranchTrackingStatus(BranchTrackingStatus status) {
		StringBuilder sb = new StringBuilder();
		int ahead = status.getAheadCount();
		int behind = status.getBehindCount();
		if (ahead != 0) {
			sb.append('\u2191');
			sb.append(ahead);
		}
		if (behind != 0) {
			if (sb.length() != 0)
				sb.append(' ');
			sb.append('\u2193');
			sb.append(status.getBehindCount());
		}
		return sb.toString();
	}

	/**
	 * @param ref
	 * @return a description of the ref, or null if the ref does not have a
	 *         description
	 */
	public static String getRefDescription(Ref ref) {
		String name = ref.getName();
		if (name.equals(Constants.HEAD)) {
			if (ref.isSymbolic())
				return UIText.GitLabelProvider_RefDescriptionHeadSymbolic;
			else
				return UIText.GitLabelProvider_RefDescriptionHead;
		} else if (name.equals(Constants.ORIG_HEAD))
			return UIText.GitLabelProvider_RefDescriptionOrigHead;
		else if (name.equals(Constants.FETCH_HEAD))
			return UIText.GitLabelProvider_RefDescriptionFetchHead;
		else if (name.equals(Constants.R_STASH))
			return UIText.GitLabelProvider_RefDescriptionStash;
		else
			return null;
	}

