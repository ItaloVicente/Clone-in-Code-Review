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

