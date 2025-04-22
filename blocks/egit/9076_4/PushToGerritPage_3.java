
			StringBuilder recivePack = new StringBuilder("git receive-pack"); //$NON-NLS-1$
			String[] reviewers = reviewersText.getText().split(
					new Character(REVIEWERS_SEPARATOR).toString());

			for (String reviewerRaw : reviewers) {
				String reviewer = reviewerRaw.trim();

				if (reviewer.matches(String.format(".*%s.*%s.*", //$NON-NLS-1$
						REVIEWERS_START_BRACKET, REVIEWERS_STOP_BRACKET))) {
					reviewer = reviewer.substring(
							reviewer.indexOf(REVIEWERS_START_BRACKET) + 1,
							reviewer.lastIndexOf(REVIEWERS_STOP_BRACKET));
				}

				if (reviewer.length() > 0) {
					recivePack.append(String.format(
							" --reviewer='%s'", reviewer)); //$NON-NLS-1$
				}
			}
			op.setReceivePack(recivePack.toString());

