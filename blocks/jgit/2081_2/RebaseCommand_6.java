	String toAuthorScript(PersonIdent author) {
		StringBuilder sb = new StringBuilder(100);
		sb.append(GIT_AUTHOR_NAME);
		sb.append("='");
		sb.append(author.getName());
		sb.append("'\n");
		sb.append(GIT_AUTHOR_EMAIL);
		sb.append("='");
		sb.append(author.getEmailAddress());
		sb.append("'\n");
		sb.append(GIT_AUTHOR_DATE);
		sb.append("='");
		String externalString = author.toExternalString();
		sb
				.append(externalString.substring(externalString
						.lastIndexOf('>') + 2));
		sb.append("'\n");
		return sb.toString();
	}

