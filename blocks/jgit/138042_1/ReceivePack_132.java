	@Override
	public final Repository getRepository() {
		return db;
	}

	@Override
	public final RevWalk getRevWalk() {
		return walk;
	}

	@Override
	public final Map<String
		return refs;
	}

	@Override
	public void setAdvertisedRefs(Map<String
		refs = allRefs != null ? allRefs : db.getAllRefs();
		refs = refFilter.filter(refs);
		advertisedHaves.clear();

		Ref head = refs.get(HEAD);
		if (head != null && head.isSymbolic()) {
			refs.remove(HEAD);
		}

		for (Ref ref : refs.values()) {
			if (ref.getObjectId() != null) {
				advertisedHaves.add(ref.getObjectId());
			}
		}
		if (additionalHaves != null) {
			advertisedHaves.addAll(additionalHaves);
		} else {
			advertisedHaves.addAll(db.getAdditionalHaves());
		}
	}

	@Override
	public PushCertificate getPushCertificate() {
		return pushCert;
	}

	@Override
	public void setPushCertificate(PushCertificate cert) {
		pushCert = cert;
	}

