				Ref followingTag = getNextTag(false, monitor);
				if (followingTag != null) {
					d.append(UIText.CommitMessageViewer_follows);
					RevCommit p = rw.parseCommit(followingTag
							.getObjectId());
					addLink(d, formatTagRef(followingTag), hyperlinks, p);
					d.append(LF);
				}
