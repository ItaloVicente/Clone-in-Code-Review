    public PerspectiveBarNewContributionItem(IWorkbenchWindow workbenchWindow) {
        super(PerspectiveBarNewContributionItem.class.getName());
        menuManager = new MenuManager();
        menuManager.add(ContributionItemFactory.PERSPECTIVES_SHORTLIST
                .create(workbenchWindow));
    }
