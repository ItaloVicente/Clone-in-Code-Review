                break;
            }
            nextRow(iterator, true);
        }
        contributionList.remove(cbItem);

        if (cbInternalIndex < insertAt) {
            insertAt--;
        }

        if (currentRow != targetRow) {
            contributionList.add(new Separator(USER_SEPARATOR));
            insertAt = contributionList.size();
        }
        insertAt = Math.min(insertAt, contributionList.size());
        contributionList.add(insertAt, cbItem);

    }

    /**
     * Restores the canonical order of this cool bar manager. The canonical
     * order is the order in which the contribution items where added.
     */
    public void resetItemOrder() {
        for (ListIterator<IContributionItem> iterator = cbItemsCreationOrder.listIterator(); iterator
                .hasNext();) {
            IContributionItem item = iterator.next();
            if ((item.getId() != null) && (item.getId().equals(USER_SEPARATOR))) {
                iterator.remove();
            }
        }
        IContributionItem[] itemsToSet = new IContributionItem[cbItemsCreationOrder
                .size()];
        cbItemsCreationOrder.toArray(itemsToSet);
        setItems(itemsToSet);
    }

    @Override
