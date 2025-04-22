			if (!repo.isBare() && !dco.getToBeDeleted().isEmpty()) {
				List<File> fileList = new ArrayList<File>();
				for (String filePath : dco.getToBeDeleted()) {
					fileList.add(new File(repo.getWorkTree()
				}
				status = new CheckoutResult(Status.NONDELETED
			}
			else
				status = CheckoutResult.OK_RESULT;
