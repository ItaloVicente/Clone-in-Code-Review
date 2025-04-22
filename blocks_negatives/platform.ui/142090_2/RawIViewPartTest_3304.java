        view.removePropertyListener(propertyListener);
        page.hideView(view);
        super.doTearDown();
    }

    private void verifySettings(IWorkbenchPart part, String expectedTitle,
            String expectedPartName, String expectedContentDescription)
            throws Exception {
        Assert.assertEquals("Incorrect view title", expectedTitle, part
                .getTitle());

        Assert.assertEquals("Incorrect title in view reference", expectedTitle,
                ref.getTitle());
        Assert.assertEquals("Incorrect part name in view reference",
                expectedPartName, ref.getPartName());
        Assert.assertEquals("Incorrect content description in view reference",
                expectedContentDescription, ref.getContentDescription());
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
        verifySettings("SomeTitle", "RawIViewPart", "SomeTitle");
        verifyEvents(false, false, false);
    }

    public void XXXtestCustomTitle() throws Throwable {
        view.setTitle("CustomTitle");
        verifySettings("CustomTitle", "RawIViewPart", "CustomTitle");
        verifyEvents(true, false, true);
    }

    /**
     * Ensures that the content description is empty when the title is the same
     * as the default part name
     */
    public void XXXtestEmptyContentDescription() throws Throwable {
        view.setTitle("RawIViewPart");
        verifySettings("RawIViewPart", "RawIViewPart", "");
        verifyEvents(true, false, true);
    }
