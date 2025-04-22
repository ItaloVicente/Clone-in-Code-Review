			ObjectReader r = repo.getObjectDatabase().newReader();
			try {
				for (String file : files)
					DirCacheCheckout.checkoutEntry(repo
							file)
			} finally {
				r.release();
			}
