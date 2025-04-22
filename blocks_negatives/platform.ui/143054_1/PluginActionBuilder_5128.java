        }

        /**
         * Inserts a contribution item after another named contribution item.
         * Subclasses may override.
         */
        protected void insertAfter(IContributionManager mgr, String refId,
                IContributionItem item) {
            mgr.insertAfter(refId, item);
        }

        /**
         * Adds a group to a contribution manager.
         * Subclasses may override.
         */
        protected void addGroup(IContributionManager mgr, String name) {
            mgr.add(new Separator(name));
        }
