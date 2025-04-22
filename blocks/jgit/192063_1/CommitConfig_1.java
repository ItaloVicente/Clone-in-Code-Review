		String comment = rc.getString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_COMMENT_CHAR);
		if (!StringUtils.isEmptyOrNull(comment)) {
				autoCommentChar = true;
			} else {
				char first = comment.charAt(0);
				if (first > ' ' && first < 127) {
					commentCharacter = first;
				}
			}
		}
