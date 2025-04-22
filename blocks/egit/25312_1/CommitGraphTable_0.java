		private String getHoverText(Ref ref, int refIndex, SWTCommit commit) {
			if (ref.getName().startsWith(Constants.R_TAGS)
					&& renderer.isShownAsEllipsis(ref)) {
				StringBuilder sb = new StringBuilder(UIText.CommitGraphTable_HoverAdditionalTags);
				for (int i = refIndex; i < commit.getRefCount(); i++) {
					Ref tag = commit.getRef(i);
					String name = tag.getName();
					if (name.startsWith(Constants.R_TAGS)) {
						sb.append('\n');
						sb.append(name.substring(Constants.R_TAGS.length()));
					}
				}
				return sb.toString();
			} else {
				return getHoverTextForSingleRef(ref);
			}
		}

		private String getHoverTextForSingleRef(Ref r) {
