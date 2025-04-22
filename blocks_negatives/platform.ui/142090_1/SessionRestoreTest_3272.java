        pages = windows[1].getPages();
        assertEquals(pages.length, 2);
        assertEquals(pages[0].getPerspective().getId(),
                EmptyPerspective.PERSP_ID);
        assertEquals(pages[1].getPerspective().getId(), SessionPerspective.ID);
        testSessionView(pages[1]);
