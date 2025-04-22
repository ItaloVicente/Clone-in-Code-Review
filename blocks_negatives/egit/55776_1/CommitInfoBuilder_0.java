		if (author != null) {
			d.append(UIText.CommitMessageViewer_author);
			d.append(author.getName());
			d.append(author.getEmailAddress());
			d.append(fmt.format(author.getWhen()));
			d.append(LF);
		}

		if (committer != null) {
			d.append(UIText.CommitMessageViewer_committer);
			d.append(committer.getName());
			d.append(committer.getEmailAddress());
			d.append(fmt.format(committer.getWhen()));
			d.append(LF);
		}
