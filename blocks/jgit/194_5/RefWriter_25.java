	public RefWriter(Map<String
		if (refs instanceof RefMap)
			this.refs = refs.values();
		else
			this.refs = RefComparator.sort(refs.values());
	}

	RefWriter(RefList<Ref> list) {
		this.refs = list.asList();
	}

