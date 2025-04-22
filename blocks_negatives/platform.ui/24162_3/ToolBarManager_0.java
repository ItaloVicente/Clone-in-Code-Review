                try {
                    if (useRedraw) {
                        toolBar.setRedraw(false);
                    }

                    for (int i = toRemove.size(); --i >= 0;) {
                        ToolItem item = toRemove.get(i);
                        if (!item.isDisposed()) {
                            Control ctrl = item.getControl();
                            if (ctrl != null) {
                                item.setControl(null);
                                ctrl.dispose();
                            }
                            item.dispose();
                        }
                    }

                    IContributionItem src, dest;
                    mi = toolBar.getItems();
                    int srcIx = 0;
                    int destIx = 0;
                    for (Iterator<IContributionItem> e = clean.iterator(); e.hasNext();) {
                        src = e.next();

                        if (srcIx < mi.length) {
