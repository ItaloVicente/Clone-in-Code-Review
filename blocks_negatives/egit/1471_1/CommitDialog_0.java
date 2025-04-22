			try {
				new PersonIdent(author);
				authorValid = true;
			} catch (IllegalArgumentException e) {
				authorValid = false;
			}
