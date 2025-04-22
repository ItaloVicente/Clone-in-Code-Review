        if (a instanceof WorkbenchWizardElement) {
            wizards.add(a);
        } else {
            super.add(a);
        }
        return this;
    }


    /**
     * Remove a wizard from this collection.
     */
    @Override
