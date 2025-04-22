	private static ReceiveCommand toCommand(Ref oldRef, Ref newRef) {
		ObjectId oldId = toId(oldRef);
		ObjectId newId = toId(newRef);
		String name = toName(oldRef, newRef);

		if (oldRef != null && oldRef.isSymbolic()) {
			if (newRef != null) {
				if (newRef.isSymbolic()) {
					return ReceiveCommand.link(oldRef.getTarget().getName(),
							newRef.getTarget().getName(), name);
				} else {
					return ReceiveCommand.unlink(oldRef.getTarget().getName(),
							newId, name);
				}
			} else {
				return ReceiveCommand.unlink(oldRef.getTarget().getName(),
						ObjectId.zeroId(), name);
			}
		}

		if (newRef != null && newRef.isSymbolic()) {
			if (oldRef != null) {
				if (oldRef.isSymbolic()) {
					return ReceiveCommand.link(oldRef.getTarget().getName(),
							newRef.getTarget().getName(), name);
				} else {
					return ReceiveCommand.link(oldId,
							newRef.getTarget().getName(), name);
				}
			} else {
				return ReceiveCommand.link(ObjectId.zeroId(),
						newRef.getTarget().getName(), name);
			}
		}

		return new ReceiveCommand(oldId, newId, name);
	}

	private static ObjectId toId(Ref ref) {
		if (ref != null) {
			ObjectId id = ref.getObjectId();
			if (id != null) {
				return id;
			}
		}
		return ObjectId.zeroId();
	}

	private static String toName(Ref oldRef, Ref newRef) {
		return oldRef != null ? oldRef.getName() : newRef.getName();
	}

