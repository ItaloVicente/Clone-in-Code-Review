		try {
			return RevUtils.getCommonAncestor(gsd.getRepository(), gsd
					.getSrcRevCommit(), gsd.getDstRevCommit());
		} catch (IOException e) {
			throw new TeamException(e.getMessage(), e);
		}
