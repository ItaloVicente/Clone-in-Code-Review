
	private Pack getSinglePack() {
		tr.getRepository().getObjectDatabase().has(unknownID);
		Collection<Pack> packs = tr.getRepository().getObjectDatabase()
				.getPacks();
		assertEquals(1
		Pack pack = packs.stream().findFirst().get();
		return pack;
	}
