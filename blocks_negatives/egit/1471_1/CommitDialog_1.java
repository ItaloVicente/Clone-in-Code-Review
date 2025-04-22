			try {
				new PersonIdent(committer);
				committerValid = true;
			} catch (IllegalArgumentException e) {
				committerValid = false;
			}
