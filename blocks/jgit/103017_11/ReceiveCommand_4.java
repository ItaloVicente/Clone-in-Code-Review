	protected ReceiveCommand(ObjectId oldId
		if (oldId == null) {
			throw new IllegalArgumentException(
					JGitText.get().oldIdMustNotBeNull);
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					JGitText.get().nameMustNotBeNullOrEmpty);
		}
		this.oldId = oldId;
		this.oldSymref = null;
		this.newId = ObjectId.zeroId();
		this.newSymref = newSymref;
		this.name = name;
		if (AnyObjectId.equals(ObjectId.zeroId()
			type = Type.CREATE;
		} else if (newSymref != null) {
			type = Type.UPDATE;
		} else {
			type = Type.DELETE;
		}
		typeIsCorrect = true;
	}

	protected ReceiveCommand(String oldSymref
		if (newId == null) {
			throw new IllegalArgumentException(
					JGitText.get().newIdMustNotBeNull);
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					JGitText.get().nameMustNotBeNullOrEmpty);
		}
		this.oldId = ObjectId.zeroId();
		this.oldSymref = oldSymref;
		this.newId = newId;
		this.newSymref = null;
		this.name = name;
		if (oldSymref == null) {
			type = Type.CREATE;
		} else if (!AnyObjectId.equals(ObjectId.zeroId()
			type = Type.UPDATE;
		} else {
			type = Type.DELETE;
		}
		typeIsCorrect = true;
	}

	protected ReceiveCommand(@Nullable String oldTarget
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					JGitText.get().nameMustNotBeNullOrEmpty);
		}
		this.oldId = ObjectId.zeroId();
		this.oldSymref = oldTarget;
		this.newId = ObjectId.zeroId();
		this.newSymref = newTarget;
		this.name = name;
		if (oldTarget == null) {
			if (newTarget == null) {
				throw new IllegalArgumentException(
						JGitText.get().bothRefTargetsMustNotBeNull);
			}
			type = Type.CREATE;
		} else if (newTarget != null) {
			type = Type.UPDATE;
		} else {
			type = Type.DELETE;
		}
		typeIsCorrect = true;
	}

