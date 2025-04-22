		String decoded = decode(Constants.CHARSET
		try {
			return Charset.forName(decoded);
		} catch (IllegalCharsetNameException badName) {
			Charset aliased = encodingAliases.get(StringUtils
					.toLowerCase(decoded));
			if (aliased != null)
				return aliased;
			throw badName;
		} catch (UnsupportedCharsetException badName) {
			Charset aliased = encodingAliases.get(StringUtils
					.toLowerCase(decoded));
			if (aliased != null)
				return aliased;
			throw badName;
		}
