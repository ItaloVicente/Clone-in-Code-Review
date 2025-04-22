			boolean enabled = containsTagNameAndMessage || shouldOverwriteTag;
			button.setEnabled(enabled);

			Button createTagAndStartPush = getButton(CREATE_AND_START_PUSH_ID);
			if (createTagAndStartPush != null)
				createTagAndStartPush.setEnabled(enabled);
