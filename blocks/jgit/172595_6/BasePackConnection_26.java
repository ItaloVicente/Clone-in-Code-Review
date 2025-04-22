			if (peeled != null && symRefTarget != null) {
				break;
			}
		}
		Ref idRef;
		if (peeled != null) {
			idRef = new ObjectIdRef.PeeledTag(Ref.Storage.NETWORK
					toId(line
		} else {
			idRef = new ObjectIdRef.PeeledNonTag(Ref.Storage.NETWORK
		}
		Ref prior = avail.put(name
		if (prior != null) {
			throw duplicateAdvertisement(name);
		}
		if (!StringUtils.isEmptyOrNull(symRefTarget)) {
			symRefs.put(name
