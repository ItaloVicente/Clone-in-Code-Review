			final SWTCommit p = (SWTCommit)commit.getParent(i);
			p.parseBody();
			d.append(UIText.CommitMessageViewer_parent);
			addLink(d, hyperlinks, p);
			d.append(p.getShortMessage());
			d.append(LF);
