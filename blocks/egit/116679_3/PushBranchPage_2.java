			if (!localBranchFound) {
				List<Ref> newRefs = new ArrayList<>(filtered.size() + 1);
				newRefs.add(new ObjectIdRef.Unpeeled(Storage.NEW, localFullName,
						ObjectId.zeroId()));
				newRefs.addAll(filtered);
				filtered = newRefs;
			}
