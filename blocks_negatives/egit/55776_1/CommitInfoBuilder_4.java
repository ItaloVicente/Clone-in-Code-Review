				Ref precedingTag = getNextTag(true, monitor);
				if (precedingTag != null) {
					d.append(UIText.CommitMessageViewer_precedes);
					RevCommit p = rw.parseCommit(precedingTag
							.getObjectId());
					addLink(d, formatTagRef(precedingTag), hyperlinks, p);
					d.append(LF);
				}
