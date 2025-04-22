	private static TestProtocol<Object> generateReachableCommitUploadPackProtocol() {
		return new TestProtocol<>((Object req, Repository db) -> {
			UploadPack up = new UploadPack(db);
			up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
			return up;
		}, null);
	}

