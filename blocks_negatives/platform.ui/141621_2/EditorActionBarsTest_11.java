            for (int i = 0; i < items.length; i++) {
                if (!(items[i] instanceof Separator) && items[i].isVisible()) {
                    fail("No toolbar for a visible action text \"" + actionText
                            + "\"");
                }
            }
