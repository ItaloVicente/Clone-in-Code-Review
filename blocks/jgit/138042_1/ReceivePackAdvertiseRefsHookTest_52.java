		try (TestRepository<Repository> s = new TestRepository<>(src)) {
			RevCommit N = s.commit().parent(B).add("q"
			s.update(R_MASTER

			PushResult r;
					src
					R_MASTER
					R_MASTER
					false
					null
			);
			try (TransportLocal t = newTransportLocalWithStrictValidation()) {
				t.setPushThin(true);
				r = t.push(PM
				dst.close();
			}
