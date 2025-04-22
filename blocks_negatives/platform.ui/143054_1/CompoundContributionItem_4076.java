        for (IContributionItem item : items) {
            int oldItemCount = menu.getItemCount();
            if (item.isVisible()) {
                item.fill(menu, index);
            }
            int newItemCount = menu.getItemCount();
            int numAdded = newItemCount - oldItemCount;
            index += numAdded;
        }
    }
