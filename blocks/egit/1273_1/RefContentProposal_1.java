			loader = db.open(objectId);

			final StringBuilder sb = new StringBuilder();
			sb.append(refName);
			sb.append('\n');
			sb.append(objectId.abbreviate(db).name());
			sb.append(" - "); //$NON-NLS-1$

			switch (loader.getType()) {
			case Constants.OBJ_COMMIT:
				RevCommit c = new RevWalk(db).parseCommit(objectId);
				appendObjectSummary(sb, UIText.RefContentProposal_commit, c
						.getAuthorIdent(), c.getFullMessage());
				break;
			case Constants.OBJ_TAG:
				RevWalk walk = new RevWalk(db);
				Tag t = walk.parseTag(objectId).asTag(walk);
				appendObjectSummary(sb, UIText.RefContentProposal_tag, t
						.getAuthor(), t.getMessage());
				break;
			case Constants.OBJ_TREE:
				sb.append(UIText.RefContentProposal_tree);
				break;
			case Constants.OBJ_BLOB:
				sb.append(UIText.RefContentProposal_blob);
				break;
			default:
				sb.append(UIText.RefContentProposal_unknownObject);
			}
			return sb.toString();
