	public static ReceiveCommand link(final ObjectId oldId
			final @NonNull String newTarget
		return new ReceiveCommand(oldId
	}

	public static ReceiveCommand link(final @Nullable String oldTarget
			final @NonNull String newTarget
		return new ReceiveCommand(oldTarget
	}

	public static ReceiveCommand unlink(final String oldTarget
			final ObjectId newId
		return new ReceiveCommand(oldTarget
	}

