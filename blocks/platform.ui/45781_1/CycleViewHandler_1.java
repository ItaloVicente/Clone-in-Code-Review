	private Collection<MPart> getPartListSortedByActivation(EPartService partService, List<MPart> parts) {
		if (partService instanceof PartServiceImpl) {
			PartServiceImpl partServiceImpl = (PartServiceImpl) partService;

			List<MPart> activationList = partServiceImpl.getActivationList();
			if (activationList.isEmpty()) {
				return parts;
			}
			Set<MPart> partList = new LinkedHashSet<>(activationList);

			partList.retainAll(parts);

			partList.addAll(parts);
			return partList;
		}

		return parts;
	}

