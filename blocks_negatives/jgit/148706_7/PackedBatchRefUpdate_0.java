		int addIdx = 0;

		Map<String, ReceiveCommand> byName = byName(commands);
		RefList.Builder<Ref> b =
				new RefList.Builder<>(refs.size() - nDeletes + adds.size());
		for (Ref ref : refs) {
			String name = ref.getName();
			ReceiveCommand cmd = byName.remove(name);
			if (cmd == null) {
				b.add(ref);
				continue;
			}
			if (!cmd.getOldId().equals(ref.getObjectId())) {
				lockFailure(cmd, commands);
				return null;
