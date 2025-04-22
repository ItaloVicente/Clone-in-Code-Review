		try {
			if (node.getRepository().getFullBranch().equals(ref.getName())) {
				deleteBranch.setEnabled(false);
			}
		} catch (IOException e2) {
		}
