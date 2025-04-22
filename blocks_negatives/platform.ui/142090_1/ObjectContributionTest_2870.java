        /*
         * Pretend to show the pop-up menu -- looking to motivate the extender
         * to fill the menu based on the selection provider.
         *
         * TODO This causes a big delay (in the order of a minute or more) while
         * trying to fill this menu. It seems to be loading a bunch of plug-ins,
         * and doing class loading.
         */
        extender.menuAboutToShow(fakeMenuManager);
