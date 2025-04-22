		try {
			return gitVariant.getMembers(progress);
		} catch (IOException e) {
			throw new TeamException(NLS.bind(
					CoreText.GitResourceVariantTree_couldNotFetchMembers,
					gitVariant), e);
		}
