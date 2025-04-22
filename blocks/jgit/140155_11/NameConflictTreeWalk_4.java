            for (AbstractTreeIterator t : trees) {
                if (t.matches == ch) {
                    if (t.matchShift == 0)
                        t.next(1);
                    else {
                        t.back(t.matchShift);
                        t.matchShift = 0;
                    }
                    t.matches = null;
                }
            }
