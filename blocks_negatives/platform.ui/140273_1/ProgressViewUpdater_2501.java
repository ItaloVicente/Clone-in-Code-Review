            Iterator<JobTreeElement> additionsIterator = additions.iterator();
            while (additionsIterator.hasNext()) {
                JobTreeElement treeElement = additionsIterator
                        .next();
                if (!treeElement.isActive()) {
                    if (deletions.contains(treeElement)) {
