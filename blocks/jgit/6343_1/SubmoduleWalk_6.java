		String leafName;
		try {
			Ref head = subRepo.getRef(Constants.HEAD);
			leafName = head != null ? head.getLeaf().getName() : null;
		} finally {
			subRepo.close();
		}

		return leafName;
