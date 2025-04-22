            for (AbstractTreeIterator t : trees) {
                if (t.matches == ch) {
                    t.next(1);
                    t.matches = null;
                }
            }
