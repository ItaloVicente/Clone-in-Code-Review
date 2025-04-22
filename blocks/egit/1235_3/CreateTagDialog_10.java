		this.commitId = null;
	}

	public CreateTagDialog(Shell parent, IInputValidator tagNameValidator,
			ObjectId commitId) {
		super(parent);
		this.tagNameValidator = tagNameValidator;
		this.branchName = null;
		this.commitId = commitId;
