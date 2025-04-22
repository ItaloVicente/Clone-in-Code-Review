		try {
			Ref head = subRepo.getRef(Constants.HEAD);
			return head != null ? head.getLeaf().getName() : null;
		} finally {
			subRepo.close();
		}
