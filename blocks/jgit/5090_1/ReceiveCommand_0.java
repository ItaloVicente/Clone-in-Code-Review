	}

	public ReceiveCommand(final ObjectId oldId
			final String name
		if (type != Type.CREATE && ObjectId.zeroId().equals(oldId))
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().illegalCombinationOfArguments
					"oldId=" + ObjectId.zeroId()
					"type=" + type));
		if (type != Type.DELETE && ObjectId.zeroId().equals(newId))
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().illegalCombinationOfArguments
					"newId=" + ObjectId.zeroId()
					"type=" + type));
		this.oldId = oldId;
		this.newId = newId;
		this.name = name;
		this.type = type;
