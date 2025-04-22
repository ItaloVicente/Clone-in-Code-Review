                }
                insertIndex = i;
            }
            if (insertIndex >= items.length) {
                return null;
            }
            return items[insertIndex];
        }

        /**
         * Returns whether the contributor is an adjunct contributor.
         *
         * @return whether the contributor is an adjunct contributor
         */
        public boolean isAdjunctContributor() {
            return adjunctActions.size() > 0;
        }

        @Override
		protected void insertAfter(IContributionManager mgr, String refId,
                IContributionItem item) {
            IContributionItem refItem = findInsertionPoint(refId, actionSetId,
                    mgr, true);
            if (refItem != null) {
                mgr.insertAfter(refItem.getId(), item);
            } else {
                WorkbenchPlugin
            }
        }

        protected void revokeContribution(WorkbenchWindow window,
                IActionBars bars, String id) {
            revokeActionSetFromMenu(window.getMenuManager(), id);

            revokeActionSetFromCoolbar(window.getCoolBarManager2(), id);
        }

        protected void revokeAdjunctCoolbarAction(ActionDescriptor ad,
                ActionSetActionBars bars) {
            String toolBarId = ad.getToolbarId();
