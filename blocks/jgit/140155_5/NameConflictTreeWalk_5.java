            for (AbstractTreeIterator t : trees) {
                if (t.matches == ch) {
                    if (t.matchShift == 0)
                        t.skip();
                    else {
                        t.back(t.matchShift);
                        t.matchShift = 0;
                    }
                    t.matches = null;
                }
            }
