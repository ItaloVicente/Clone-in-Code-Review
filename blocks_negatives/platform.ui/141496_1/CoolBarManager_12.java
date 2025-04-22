        int targetRow = itemLocation.get(cbItem).intValue();

        int cbInternalIndex = contributionList.indexOf(cbItem);

        int insertAt = contributionList.size();
        ListIterator<IContributionItem> iterator = contributionList.listIterator();
        collapseSeparators(iterator);
        int currentRow = -1;
        while (iterator.hasNext()) {

            currentRow++;
            if (currentRow == targetRow) {
                int virtualIndex = 0;
                insertAt = iterator.nextIndex();
                while (iterator.hasNext()) {
                    IContributionItem item = iterator
                            .next();
                    Integer itemRow = itemLocation.get(item);
                    if (item.isSeparator()) {
