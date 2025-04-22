        }
        fail("Unable to find menu manager");
        return null;
    }

    /**
     * Tests the enablement / visibility of an action.
     */
    private void testAction(MenuManager mgr, String action, boolean expected)
            throws Throwable {
        assertEquals(action, expected, ActionUtil.getActionWithLabel(mgr,
                action).isEnabled());
    }
