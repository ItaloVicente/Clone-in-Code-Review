		PersonIdent person = commit.getAuthorIdent();
		if (person == null)
			person = commit.getCommitterIdent();
		if (person != null)
			styled.append(MessageFormat.format(
					UIText.RepositoryCommit_UserAndDate, person.getName(),
					formatDate(person.getWhen())),
					StyledString.QUALIFIER_STYLER);
