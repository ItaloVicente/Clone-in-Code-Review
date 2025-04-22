			Ref followingTag = getNextTag(false);
			if (followingTag != null) {
				d.append(UIText.CommitMessageViewer_follows);
				RevCommit p = new RevWalk(db).parseCommit(followingTag
						.getObjectId());
				addLink(d, formatTagRef(followingTag), styles, p);
				d.append(LF);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}

		try {
			Ref precedingTag = getNextTag(true);
			if (precedingTag != null) {
				d.append(UIText.CommitMessageViewer_precedes);
				RevCommit p = new RevWalk(db).parseCommit(precedingTag
						.getObjectId());
				addLink(d, formatTagRef(precedingTag), styles, p);
				d.append(LF);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}

		makeGrayText(d, styles);
		d.append(LF);
		String msg = commit.getFullMessage();
		if (fill) {
			Matcher spm = p.matcher(msg);
			if (spm.find()) {
				String subMsg = msg.substring(0, spm.end());
				msg = subMsg.replaceAll("([\\w.,; \t])\n(\\w)", "$1 $2") //$NON-NLS-1$ //$NON-NLS-2$
						+ msg.substring(spm.end());
			}
		}
		int h0 = d.length();
		d.append(msg);
		d.append(LF);
