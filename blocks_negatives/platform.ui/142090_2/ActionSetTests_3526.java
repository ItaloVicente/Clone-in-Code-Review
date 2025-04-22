        assertNull(window.getActionBars().getMenuManager().findUsingPath("menu1"));
        assertNull(getActionSetRegistry().findActionSet(ACTION_SET_ID));
        LeakTests.checkRef(queue, ref);
        findInPresentation(window, action, found);
        assertFalse("Action set found", found[0]);
        assertNull("Action found", action[0]);
