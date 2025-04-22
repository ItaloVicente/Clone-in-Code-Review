                super.partHidden(ref);
                assertEquals(MockViewPart.ID, ref.getId());
                assertNull(fPage.findView(MockViewPart.ID));
            }
        };
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        IPerspectiveDescriptor emptyPerspDesc2 = fWindow.getWorkbench()
                .getPerspectiveRegistry().findPerspectiveWithId(
                        EmptyPerspective.PERSP_ID2);
        fPage.setPerspective(emptyPerspDesc2);
        MockViewPart view2 = (MockViewPart) fPage.showView(MockViewPart.ID);
        assertTrue(view == view2);
        fPage.addPartListener(listener);
        clearEventState();
        fPage.hideView(view);
        assertTrue(history2.contains("partHidden"));
        assertEquals(getRef(view), eventPartRef);
        fPage.removePartListener(listener);
    }

    /**
     * Tests the partHidden method by activating another view in the same folder.
     */
    public void testPartHiddenWhenObscured() throws Throwable {
        final boolean[] eventReceived = { false };
        IPartListener2 listener = new TestPartListener2() {
            @Override
