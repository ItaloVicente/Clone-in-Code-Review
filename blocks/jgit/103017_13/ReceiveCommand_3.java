	public static ReceiveCommand link(@NonNull ObjectId oldId
			@NonNull String newTarget
		return new ReceiveCommand(oldId
	}

	public static ReceiveCommand link(@Nullable String oldTarget
			@NonNull String newTarget
		return new ReceiveCommand(oldTarget
	}

	public static ReceiveCommand unlink(@NonNull String oldTarget
			@NonNull ObjectId newId
		return new ReceiveCommand(oldTarget
	}

