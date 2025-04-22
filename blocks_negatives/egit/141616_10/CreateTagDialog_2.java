			boolean containsTagNameAndMessage = (tagNameMessage == null || tagMessageVal
					.length() == 0) && tagMessageVal.length() != 0;
			boolean shouldOverwriteTag = (overwriteButton.getSelection() && Repository
					.isValidRefName(Constants.R_TAGS + tagNameText.getText()));

			boolean enabled = containsTagNameAndMessage || shouldOverwriteTag;
