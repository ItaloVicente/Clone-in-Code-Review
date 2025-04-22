		testProtocol = new TestProtocol<>((Object req
			UploadPack up = new UploadPack(db);
			up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
			up.getRevWalk()
					.assumeShallow(Collections.singleton(commit1.getId()));
			return up;
		}
