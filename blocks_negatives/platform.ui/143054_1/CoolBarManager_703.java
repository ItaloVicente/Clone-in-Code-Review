        Assert.isNotNull(toolBarManager);
        super.add(new ToolBarContributionItem(toolBarManager));
    }

    /**
     * Collapses consecutive separators and removes a separator from the
     * beginning and end of the list.
     *
     * @param contributionList
     *            the list of contributions; must not be <code>null</code>.
     * @return The contribution list provided with extraneous separators
     *         removed; this value is never <code>null</code>, but may be
     *         empty.
     */
    private ArrayList<IContributionItem> adjustContributionList(ArrayList<IContributionItem> contributionList) {
        IContributionItem item;
        if (contributionList.size() != 0) {
            item = contributionList.get(0);
            if (item.isSeparator()) {
                contributionList.remove(0);
            }

            ListIterator<IContributionItem> iterator = contributionList.listIterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                if (item.isSeparator()) {
                    while (iterator.hasNext()) {
                        item = iterator.next();
                        if (item.isSeparator()) {
                            iterator.remove();
                        } else {
                            break;
                        }
                    }

                }
            }
            if (contributionList.size() != 0) {
	            item = contributionList.get(contributionList
	                    .size() - 1);
	            if (item.isSeparator()) {
	                contributionList.remove(contributionList.size() - 1);
	            }
            }
        }
        return contributionList;

    }

    @Override
