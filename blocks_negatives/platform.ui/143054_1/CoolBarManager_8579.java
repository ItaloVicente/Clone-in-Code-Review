        if ((!isDirty() && !force) || (!coolBarExist())) {
            return;
        }

        boolean relock = false;
        boolean changed = false;

        try {
            coolBar.setRedraw(false);

            refresh();

            if (coolBar.getLocked()) {
                coolBar.setLocked(false);
                relock = true;
            }

            /*
             * Make a list of items including only those items that are
             * visible. Separators should stay because they mark line breaks in
             * a cool bar.
             */
            final IContributionItem[] items = getItems();
            final List<IContributionItem> visibleItems = new ArrayList<>(items.length);
            for (final IContributionItem item : items) {
                if (isChildVisible(item)) {
                    visibleItems.add(item);
                }
            }

            /*
             * Make a list of CoolItem widgets in the cool bar for which there
             * is no current visible contribution item. These are the widgets
             * to be disposed. Dynamic items are also removed.
             */
            CoolItem[] coolItems = coolBar.getItems();
            final ArrayList<CoolItem> coolItemsToRemove = new ArrayList<>(coolItems.length);
            for (CoolItem coolItem : coolItems) {
                final Object data = coolItem.getData();
                if ((data == null)
                        || (!visibleItems.contains(data))
                        || ((data instanceof IContributionItem) && ((IContributionItem) data)
                                .isDynamic())) {
                    coolItemsToRemove.add(coolItem);
                }
            }

            for (int i = coolItemsToRemove.size() - 1; i >= 0; i--) {
                CoolItem coolItem = coolItemsToRemove.get(i);
                if (!coolItem.isDisposed()) {
                    Control control = coolItem.getControl();
                    if (control != null) {
                        coolItem.setControl(null);
                        control.dispose();
                    }
                    coolItem.dispose();
                }
            }

            coolItems = coolBar.getItems();
            IContributionItem sourceItem;
            IContributionItem destinationItem;
            int sourceIndex = 0;
            int destinationIndex = 0;
            final Iterator<IContributionItem> visibleItemItr = visibleItems.iterator();
            while (visibleItemItr.hasNext()) {
                sourceItem = visibleItemItr.next();

                if (sourceIndex < coolItems.length) {
                    destinationItem = (IContributionItem) coolItems[sourceIndex]
                            .getData();
                } else {
                    destinationItem = null;
                }

                if (destinationItem != null) {
                    if (sourceItem.equals(destinationItem)) {
                        sourceIndex++;
                        destinationIndex++;
                        sourceItem.update();
                        continue;

                    } else if ((destinationItem.isSeparator())
                            && (sourceItem.isSeparator())) {
                        coolItems[sourceIndex].setData(sourceItem);
                        sourceIndex++;
                        destinationIndex++;
                        sourceItem.update();
                        continue;

                    }
                }

                final int start = coolBar.getItemCount();
                if (sourceItem instanceof ToolBarContributionItem) {
	                IToolBarManager manager = ((ToolBarContributionItem)sourceItem).getToolBarManager();
	            	if(manager instanceof IToolBarManager2) {
	            		((IToolBarManager2)manager).setOverrides(getOverrides());
	            	}
                }
                sourceItem.fill(coolBar, destinationIndex);
                final int newItems = coolBar.getItemCount() - start;
                for (int i = 0; i < newItems; i++) {
                    coolBar.getItem(destinationIndex++).setData(sourceItem);
                }
                changed = true;
            }

            for (int i = coolItems.length - 1; i >= sourceIndex; i--) {
                final CoolItem item = coolItems[i];
                if (!item.isDisposed()) {
                    Control control = item.getControl();
                    if (control != null) {
                        item.setControl(null);
                        control.dispose();
                    }
                    item.dispose();
                    changed = true;
                }
            }

            updateWrapIndices();

            for (IContributionItem item : items) {
                item.update(SIZE);
            }

            if (relock) {
                coolBar.setLocked(true);
            }

            if (changed) {
                updateTabOrder();
            }

            setDirty(false);
        } finally {
            coolBar.setRedraw(true);
        }
    }

    /**
     * Sets the tab order of the coolbar to the visual order of its items.
     */
    /* package */void updateTabOrder() {
        if (coolBar != null) {
            CoolItem[] items = coolBar.getItems();
            if (items != null) {
                ArrayList<Control> children = new ArrayList<>(items.length);
                for (int i = 0; i < items.length; i++) {
                    if ((items[i].getControl() != null)
                            && (!items[i].getControl().isDisposed())) {
                        children.add(items[i].getControl());
                    }
                }
                Control[] childrenArray = new Control[0];
                childrenArray = children.toArray(childrenArray);

                if (childrenArray != null) {
                    coolBar.setTabList(childrenArray);
                }

            }
        }
    }

    /**
     * Updates the indices at which the cool bar should wrap.
     */
    private void updateWrapIndices() {
        final IContributionItem[] items = getItems();
        final int numRows = getNumRows(items) - 1;

        final int[] wrapIndices = new int[numRows];
        boolean foundSeparator = false;
        int j = 0;
        CoolItem[] coolItems = (coolBar == null) ? null : coolBar.getItems();
