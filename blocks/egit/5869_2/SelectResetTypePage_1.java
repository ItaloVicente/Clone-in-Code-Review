		setMessage(UIText.SelectResetTypePage_PageMessage);

		repo = repository;
		current = currentRef;
		target = targetRef;
	}

	private Image getIcon(final String ref) {
		if (ref.startsWith(Constants.R_TAGS))
			return UIIcons.TAG.createImage();
		else if (ref.startsWith(Constants.R_HEADS)
				|| ref.startsWith(Constants.R_REMOTES))
			return UIIcons.BRANCH.createImage();
