        view.removePropertyListener(propertyListener);
        page.hideView(view);
        super.doTearDown();
    }

    private static void verifySettings(IWorkbenchPart2 part,
            String expectedTitle, String expectedPartName,
            String expectedContentDescription) throws Exception {
        Assert.assertEquals("Incorrect view title", expectedTitle, part
                .getTitle());
        Assert.assertEquals("Incorrect part name", expectedPartName, part
                .getPartName());
        Assert.assertEquals("Incorrect content description",
                expectedContentDescription, part.getContentDescription());
    }

    private void verifySettings(String expectedTitle, String expectedPartName,
            String expectedContentDescription) throws Exception {
        verifySettings(view, expectedTitle, expectedPartName,
                expectedContentDescription);
    }

    /**
     * Ensure that we've received the given property change events since the start of the test
     *
     * @param titleEvent PROP_TITLE
     * @param nameEvent PROP_PART_NAME
     * @param descriptionEvent PROP_CONTENT_DESCRIPTION
     */
    private void verifyEvents(boolean titleEvent, boolean nameEvent,
            boolean descriptionEvent) {
        if (titleEvent) {
            Assert.assertEquals("Missing title change event", titleEvent,
                    titleChangeEvent);
        }
        if (nameEvent) {
            Assert.assertEquals("Missing name change event", nameEvent,
                    nameChangeEvent);
        }
        if (descriptionEvent) {
            Assert.assertEquals("Missing content description event",
                    descriptionEvent, contentChangeEvent);
        }
    }

    public void testDefaults() throws Throwable {
        verifySettings("OverriddenTitle", "OverriddenTitleView",
                "OverriddenTitle");
        verifyEvents(false, false, false);
    }

    public void testCustomName() throws Throwable {
        view.setPartName("CustomPartName");
        verifySettings("OverriddenTitle", "CustomPartName", "OverriddenTitle");
        verifyEvents(false, true, false);
    }

    public void testEmptyContentDescription() throws Throwable {
        view.setContentDescription("");
        verifySettings("OverriddenTitle", "OverriddenTitleView", "");
        verifyEvents(false, false, true);
    }

    public void testCustomTitle() throws Throwable {
        view.customSetTitle("CustomTitle");
        verifySettings("CustomTitle", "OverriddenTitleView", "CustomTitle");
        verifyEvents(true, false, true);
    }

    public void testCustomContentDescription() throws Throwable {
        view.setContentDescription("CustomContentDescription");
        verifySettings("OverriddenTitle", "OverriddenTitleView",
                "CustomContentDescription");
        verifyEvents(false, false, true);
    }

    public void testCustomNameAndContentDescription() throws Throwable {
        view.setPartName("CustomName");
        view.setContentDescription("CustomContentDescription");
        verifySettings("OverriddenTitle", "CustomName",
                "CustomContentDescription");
        verifyEvents(false, true, true);
    }
