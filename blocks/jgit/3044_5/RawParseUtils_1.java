		String decoded = decode(Constants.CHARSET
		try {
			return Charset.forName(decoded);
		} catch (IllegalCharsetNameException badName) {
			Charset aliased = charsetForAlias(decoded);
			if (aliased != null)
				return aliased;
			throw badName;
		} catch (UnsupportedCharsetException badName) {
			Charset aliased = charsetForAlias(decoded);
			if (aliased != null)
				return aliased;
			throw badName;
		}
