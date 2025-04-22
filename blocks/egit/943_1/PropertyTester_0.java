		if (property.equals("canMerge")) { //$NON-NLS-1$
			Repository rep = node.getRepository();
			try {
				return rep.getFullBranch().startsWith(Constants.R_HEADS);
			} catch (IOException e) {
				return false;
			}
		}
