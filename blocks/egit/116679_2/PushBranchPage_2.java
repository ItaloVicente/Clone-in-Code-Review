			String fullName = Constants.R_HEADS + localBranchName;
			boolean found = false;
			for (Ref ref : refs) {
				if (fullName.equalsIgnoreCase(ref.getName())) {
					found = true;
					break;
				}
			}
			if (!found) {
				List<Ref> newRefs = new ArrayList<>(refs.size() + 1);
				newRefs.add(new ObjectIdRef.Unpeeled(Storage.NEW, fullName,
						ObjectId.zeroId()));
				newRefs.addAll(refs);
				return newRefs;
			}
