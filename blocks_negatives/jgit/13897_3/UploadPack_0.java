		else {
			advertised = new HashSet<ObjectId>();
			for (Ref ref : getAdvertisedOrDefaultRefs().values()) {
				if (ref.getObjectId() != null)
					advertised.add(ref.getObjectId());
			}
		}
