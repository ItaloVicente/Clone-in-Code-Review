		return (contents, position) -> {
			IContentProposal[] proposals = new IContentProposal[validUsers.length];
			for (int i = 0; i < validUsers.length; i++) {
				final String user = validUsers[i];
				proposals[i] = new IContentProposal() {
					@Override
					public String getContent() {
						return user;
					}

					@Override
					public String getLabel() {
						return user;
					}

					@Override
					public String getDescription() {
						if (showSecondaryPopup && !user.equals("tori")) {
							return MessageFormat.format(TaskAssistExampleMessages.ExampleDialog_ProposalDescription, user);
