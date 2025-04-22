		StringBuilder s = new StringBuilder();
		s.append(commitId.abbreviate(7).name());
		s.append("..."); //$NON-NLS-1$
		if (authorIdent != null) {
			s.append(" ("); //$NON-NLS-1$
			s.append(authorIdent.getName());
			s.append(")"); //$NON-NLS-1$
		}
		return s.toString();
