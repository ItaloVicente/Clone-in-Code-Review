		Collection<Ref> remoteRefs = getRemoteRefs(uri);
		Ref myref = null;
		for (Ref ref : remoteRefs) {
			if (ref.getName().equals("refs/heads/master")) {
				myref = ref;
				break;
			}
		}
