			if (refDb instanceof RefTreeDatabase) {
				RefTreeDatabase d = (RefTreeDatabase) refDb;
				txnNamespace = d.getTxnNamespace();
				txnCommitted = d.getTxnCommitted();
				refDb = d.getBootstrap();
			}

			errw.format("Rebuilding %s from %s"
					txnCommitted
			errw.println();
			errw.flush();
