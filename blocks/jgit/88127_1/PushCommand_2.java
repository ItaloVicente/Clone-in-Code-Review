	public List<RefLeaseSpec> getRefLeaseSpecs() {
		return new ArrayList<RefLeaseSpec>(refLeaseSpecs.values());
	}

	public PushCommand setRefLeaseSpecs(RefLeaseSpec... specs) {
		return setRefLeaseSpecs(Arrays.asList(specs));
	}

	public PushCommand setRefLeaseSpecs(List<RefLeaseSpec> specs) {
		checkCallable();
		this.refLeaseSpecs.clear();
		for (RefLeaseSpec spec : specs) {
			refLeaseSpecs.put(spec.getRef()
		}
		return this;
	}

