	public FetchGerritChangePage(Repository repository, String refName) {
		super(FetchGerritChangePage.class.getName());
		Assert.isNotNull(repository);
		this.repository = repository;
		this.refName = refName;
		setTitle(NLS.bind(UIText.FetchGerritChangePage_PageTitle,
				RepositoryUtil.INSTANCE.getRepositoryName(repository)));
		setMessage(UIText.FetchGerritChangePage_PageMessage);
		settings = getDialogSettings();
		lastUriKey = repository + GerritDialogSettings.LAST_URI_SUFFIX;

		branchValidator = ValidationUtils.getRefNameInputValidator(repository,
				Constants.R_HEADS, true);
		tagValidator = ValidationUtils.getRefNameInputValidator(repository,
				Constants.R_TAGS, true);
