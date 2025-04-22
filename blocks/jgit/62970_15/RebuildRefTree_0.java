			if (refDb instanceof RefTreeDatabase) {
				RefTreeDatabase d = (RefTreeDatabase) refDb;
				refDb = d.getBootstrap();
				txnNamespace = d.getTxnNamespace();
				txnCommitted = d.getTxnCommitted();
			} else {
				RefTreeDatabase d = new RefTreeDatabase(db
				txnNamespace = d.getTxnNamespace();
				txnCommitted = d.getTxnCommitted();
			}

			errw.format("Rebuilding %s from %s"
					txnCommitted
			errw.println();
			errw.flush();
