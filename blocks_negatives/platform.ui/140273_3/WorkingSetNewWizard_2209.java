        if (descriptors.length > 1) {
            page = workingSetTypePage = new WorkingSetTypePage(this.descriptors);
        } else {
            editPageId = descriptors[0].getId();
            page = workingSetEditPage = registry.getWorkingSetPage(editPageId);
        }
        page.setWizard(this);
        addPage(page);
        setForcePreviousAndNextButtons(descriptors.length > 1);
    }
