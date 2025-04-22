		public String getAuthor() {
			if (author == null)
				return ""; //$NON-NLS-1$
			else
				return author.getName();
		}

		public String getAuthoredDate() {
			if (author == null)
				return ""; //$NON-NLS-1$
			else
				return new GitDateFormatter(Format.LOCALE)
						.formatDate(author);
		}

