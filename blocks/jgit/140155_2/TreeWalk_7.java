            for (AbstractTreeIterator t : trees) {
                if (t.matches == ch) {
                    t.skip();
                    t.matches = null;
                }
            }
