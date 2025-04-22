	static DhtRef fromData(String name
		if (data.hasSymref())
			return new DhtSymbolicRef(name
		else
			return new DhtObjectIdRef(name
	}
