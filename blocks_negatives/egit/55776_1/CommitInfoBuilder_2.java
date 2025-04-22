			final SWTCommit p = (SWTCommit)commit.getChild(i);
			p.parseBody();
			d.append(UIText.CommitMessageViewer_child);
			addLink(d, hyperlinks, p);
			d.append(p.getShortMessage());
			d.append(LF);
