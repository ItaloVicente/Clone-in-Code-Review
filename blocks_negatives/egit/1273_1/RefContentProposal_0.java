
		final StringBuilder sb = new StringBuilder();
		sb.append(refName);
		sb.append('\n');
		sb.append(objectId.abbreviate(db).name());
		if (obj instanceof Commit) {
			final Commit c = ((Commit) obj);
			appendObjectSummary(sb, UIText.RefContentProposal_commit, c
					.getAuthor(), c.getMessage());
		} else if (obj instanceof Tag) {
			final Tag t = ((Tag) obj);
			appendObjectSummary(sb, UIText.RefContentProposal_tag, t
					.getAuthor(), t.getMessage());
		} else if (obj instanceof Tree) {
			sb.append(UIText.RefContentProposal_tree);
		} else if (obj instanceof Blob) {
			sb.append(UIText.RefContentProposal_blob);
		} else
			sb.append(UIText.RefContentProposal_unknownObject);
		return sb.toString();
