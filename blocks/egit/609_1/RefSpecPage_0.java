
			if (!pushPage) {
				tagsAutoFollowButton.setSelection(false);
				tagsFetchTagsButton.setSelection(false);
				tagsNoTagsButton.setSelection(false);

				final TagOpt tagOpt = newRepoSelection.getConfig().getTagOpt();
				switch (tagOpt) {
				case AUTO_FOLLOW:
					tagsAutoFollowButton.setSelection(true);
					break;
				case FETCH_TAGS:
					tagsFetchTagsButton.setSelection(true);
					break;
				case NO_TAGS:
					tagsNoTagsButton.setSelection(true);
					break;
				}
