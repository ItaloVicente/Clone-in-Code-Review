			int maxNumberOfTags = 1;
			if (tagCount == maxNumberOfTags) {
				txt = name.substring(Constants.R_TAGS.length());
			} else if (tagCount == maxNumberOfTags + 1) {
				txt = ELLIPSIS;
				ellipsisTags.add(name);
			} else {
				return 0;
			}
