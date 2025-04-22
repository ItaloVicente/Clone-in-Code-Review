        for (int nX = insertIndex + 1; nX < items.length; nX++) {
            IContributionItem item = items[nX];
            if (item.isSeparator() || item.isGroupMarker()) {
                break;
            }
            if (item instanceof IActionSetContributionItem) {
                if (sortId != null) {
                    String testId = ((IActionSetContributionItem) item)
                            .getActionSetId();
                    if (sortId.compareTo(testId) < compareMetric) {
