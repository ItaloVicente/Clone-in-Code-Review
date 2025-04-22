        this.page = mainPage;
        this.wizardCategories = wizardCategories;
        this.primaryWizards = primaryWizards;
        this.projectsOnly = projectsOnly;

        trimPrimaryWizards();

        if (this.primaryWizards.length > 0) {
            if (allPrimary(wizardCategories)) {
                needShowAll = false;
            } else {
                needShowAll = !allActivityEnabled(wizardCategories);
            }
        } else {
            needShowAll = !allActivityEnabled(wizardCategories);
        }
