        /**
         * Creates the menu manager for the drop-down.
         */
        private void createDropDownMenuMgr() {
            if (dropDownMenuMgr == null) {
                dropDownMenuMgr = new MenuManager();
                dropDownMenuMgr.add(newWizardMenu);
            }
        }
