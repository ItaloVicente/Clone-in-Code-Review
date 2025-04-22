		public String getAuthor() {
			if (author == null)
				return ""; //$NON-NLS-1$
			else
				return author.getName();
		}

		public String getAuthoredDate(GitDateFormatter dateFormatter) {
			if (author == null)
				return ""; //$NON-NLS-1$
			else
				return dateFormatter.formatDate(author);
		}

		public String getCommitter() {
			if (committer == null)
				return ""; //$NON-NLS-1$
			else
				return committer.getName();
		}

		public String getCommittedDate(GitDateFormatter dateFormatter) {
			if (committer == null)
				return ""; //$NON-NLS-1$
			else
				return dateFormatter.formatDate(committer);
		}

