	private static boolean matchOld(ReceiveCommand cmd, @Nullable Ref ref) {
		if (ref == null) {
			return AnyObjectId.isEqual(ObjectId.zeroId(), cmd.getOldId())
					&& cmd.getOldSymref() == null;
		} else if (ref.isSymbolic()) {
			return ref.getTarget().getName().equals(cmd.getOldSymref());
		}
		ObjectId id = ref.getObjectId();
		if (id == null) {
			id = ObjectId.zeroId();
		}
		return cmd.getOldId().equals(id);
	}

	private void applyUpdates(RevWalk rw, List<ReceiveCommand> pending)
