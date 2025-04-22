		remoteRefs = getRemoteRefs(uri);
		myref = null;
		for (Ref ref : remoteRefs) {
			if (ref.getName().equals("refs/heads/master")) {
				myref = ref;
				break;
			}
		}
		op = new CloneOperation(uri, true, null, workdir, myref,
