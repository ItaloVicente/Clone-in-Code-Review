		this(repository);
		this.refName = refName;
	}

	@Override
	public void addPages() {
		page = new FetchGerritChangePage(repository, refName);
		addPage(page);
