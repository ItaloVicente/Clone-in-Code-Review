			final String authorName = "First Author";
			final String authorEmail = "author@example.org";
			final Date authorDate = new Date(1349621117000L);
			PersonIdent firstAuthor = new PersonIdent(authorName
					authorDate
			git.commit().setMessage("initial commit").setAuthor(firstAuthor).call();
