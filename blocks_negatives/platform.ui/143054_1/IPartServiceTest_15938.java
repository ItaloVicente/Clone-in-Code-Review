                super.partHidden(ref);
                assertEquals(MockViewPart.ID, ref.getId());
                assertNull(fPage.findView(MockViewPart.ID));
            }
        };
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        fPage.addPartListener(listener);
        clearEventState();
        fPage.hideView(view);
        assertTrue(history2.contains("partHidden"));
        assertEquals(getRef(view), eventPartRef);
        fPage.removePartListener(listener);
    }

    /**
     * Tests the partHidden method by closing a view when it is shared with another perspective.
     * Includes regression test for:
     *   Bug 60039 [ViewMgmt] (regression) IWorkbenchPage#findView returns non-null value after part has been closed
     */
    public void XXXtestPartHiddenWhenClosedAndShared() throws Throwable {
        IPartListener2 listener = new TestPartListener2() {
            @Override
