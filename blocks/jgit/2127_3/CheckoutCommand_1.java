			try {
				dco.checkout();
			} catch (CheckoutConflictException e) {
				List<File> fileList = new ArrayList<File>();
				for (String filePath : dco.getConflicts()) {
					fileList.add(new File(repo.getWorkTree()
				}
				status = new CheckoutResult(Status.CONFLICTS
				throw e;
			}
