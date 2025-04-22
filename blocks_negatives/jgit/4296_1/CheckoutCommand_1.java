			}

			File workTree = repo.getWorkTree();
			ObjectReader r = repo.getObjectDatabase().newReader();
			try {
				for (String file : files)
					DirCacheCheckout.checkoutEntry(repo, new File(workTree,
							file), dc.getEntry(file), r);
