	public static ReceiveCommand link(ObjectId oldId
			String name) {
		return new ReceiveCommand(oldId
	}

	public static ReceiveCommand link(@Nullable String oldTarget
			@NonNull String newTarget
		return new ReceiveCommand(oldTarget
	}

	public static ReceiveCommand unlink(String oldTarget
			String name) {
		return new ReceiveCommand(oldTarget
	}

