				RevCommit commit = fileDiff.getCommit();
				RevCommit base = fileDiff.getBase();
				String msg;
				if (base == null || base.equals(commit.getParent(0))) {
					if (base == null) {
						base = commit.getParent(0);
					}
					msg = UIText.DiffViewer_OpenPreviousLinkLabel;
				} else {
					msg = UIText.DiffViewer_OpenBaseLinkLabel;
				}
				return MessageFormat.format(msg, Utils.getShortObjectId(base));
