	public enum DiffHeaderFormat {
		NONE(CoreText.DiffHeaderFormat_None, false, null),

		EMAIL(CoreText.DiffHeaderFormat_Email, true, "From ${sha1} ${date}\nFrom: ${author}\nDate: ${author date}\nSubject: [PATCH] ${title line}\n${full commit message}\n"), //$NON-NLS-1$

		ONELINE(CoreText.DiffHeaderFormat_Oneline, true, "${sha1} ${title line}\n"); //$NON-NLS-1$

		private final String description;

		private final boolean commitRequired;

		private final String template;

		private DiffHeaderFormat(final String d, final boolean c, final String t) {
			description = d;
			commitRequired = c;
			template = t;
		}

		public boolean isCommitRequired() {
			return commitRequired;
		}

		public String getTemplate() {
			return template;
		}

		public String getDescription() {
			return description;
		}
	}

	enum DiffHeaderKeyword{
		SHA1, AUTHOR_DATE, AUTHOR, DATE, TITLE_LINE, FULL_COMMIT_MESSAGE
	}

