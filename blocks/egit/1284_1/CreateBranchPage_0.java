		setMessage(UIText.CreateBranchPage_ChooseBranchAndNameMessage);
	}

	public CreateBranchPage(Repository repo, RevCommit baseCommit) {
		super(CreateBranchPage.class.getName());
		commitMode = true;
		this.myRepository = repo;
		this.myBaseBranch = null;
		this.myBaseCommit = baseCommit;
		setTitle(UIText.CreateBranchPage_Title);
		setMessage(UIText.CreateBranchPage_ChooseNameMessage);
