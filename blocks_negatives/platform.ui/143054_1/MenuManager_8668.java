                    if (dest != null && src.equals(dest)) {
                        srcIx++;
                        destIx++;
                    } else if (dest != null && dest.isSeparator()
                            && src.isSeparator()) {
                        mi[srcIx].setData(src);
                        srcIx++;
                        destIx++;
                    } else {
                        int start = getMenuItemCount();
                        doItemFill(src, destIx);
                        int newItems = getMenuItemCount() - start;
                        for (int i = 0; i < newItems; i++) {
                            Item item = getMenuItem(destIx++);
                            item.setData(src);
                        }
                    }

                    if (recursive) {
                        IContributionItem item = src;
                        if (item instanceof SubContributionItem) {
