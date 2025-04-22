			switch (c) {
			case '*': {
				final AbstractHead head = createWildCardHead(
						invalidWildgetCharacter, true);
				heads.add(head);
				break;
			}
			case '?': {
				final AbstractHead head = createWildCardHead(
						invalidWildgetCharacter, false);
				heads.add(head);
				break;
			}
			default:
