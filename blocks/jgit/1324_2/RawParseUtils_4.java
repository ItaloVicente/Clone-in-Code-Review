		final String name = decode(cs
		final String email = decode(cs

		final int tzBegin = lastIndexOfTrim(raw
			return new PersonIdent(name

		final int whenBegin = Math.max(emailE
				lastIndexOfTrim(raw
			return new PersonIdent(name
