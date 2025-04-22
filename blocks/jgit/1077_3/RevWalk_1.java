	public RevTag parseTag(final AnyObjectId id) throws MissingObjectException
			IncorrectObjectTypeException
		RevObject c = parseAny(id);
		if (!(c instanceof RevTag))
			throw new IncorrectObjectTypeException(id.toObjectId()
					Constants.TYPE_TAG);
		return (RevTag) c;
	}

