		String headRefName = symRefs.get(Constants.HEAD);
		if (headRefName != null && !refMap.containsKey(headRefName)) {
			Ref headRef = refMap.get(Constants.HEAD);
			if (headRef != null) {
				ObjectId headObj = headRef.getObjectId();
				headRef = new ObjectIdRef.PeeledNonTag(Ref.Storage.NETWORK
						headRefName
				refMap.put(headRefName
				headRef = new SymbolicRef(Constants.HEAD
				refMap.put(Constants.HEAD
				symRefs.remove(Constants.HEAD);
			}
		}
