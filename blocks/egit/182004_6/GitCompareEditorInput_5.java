		int result = super.hashCode();
		result = result * 31 + Objects.hash(leftVersion, rightVersion);
		Repository repo = getRepository();
		if (repo != null) {
			result = result * 31 + repo.getDirectory().hashCode();
		}
