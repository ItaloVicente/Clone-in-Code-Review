	private static boolean matchOld(ReceiveCommand cmd
		if (ref == null) {
			return AnyObjectId.isEqual(ObjectId.zeroId()
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

	protected abstract void applyUpdates(List<Ref> newRefs
			List<ReceiveCommand> pending) throws IOException;

