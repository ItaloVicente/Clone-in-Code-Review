			int innertHint = innerwHint;

			Point tcsize = NULL_SIZE;
			if (textClient != null) {
				tcsize = textClientCache.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			}
			Point size = NULL_SIZE;
