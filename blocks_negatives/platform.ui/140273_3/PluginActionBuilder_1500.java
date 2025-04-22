            cache.add(currentContribution);
            currentContribution = null;
            return true;
        }

        if (tag.equals(IWorkbenchRegistryConstants.TAG_MENU)) {
            currentContribution.addMenu(element);
            return true;
        }

        if (tag.equals(IWorkbenchRegistryConstants.TAG_ACTION)) {
            currentContribution.addAction(createActionDescriptor(element));
            return true;
        }

        return false;
    }

    /**
     * Helper class to collect the menus and actions defined within a
     * contribution element.
     */
    protected static class BasicContribution {
