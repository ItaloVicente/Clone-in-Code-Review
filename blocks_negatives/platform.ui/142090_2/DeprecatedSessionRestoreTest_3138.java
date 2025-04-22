        IEditorPart[] editors = pages[1].getEditors();
        assertEquals(editors.length, 3);
        testSessionEditor(editors[0], SessionCreateTest.TEST_FILE_1);
        testSessionEditor(editors[1], SessionCreateTest.TEST_FILE_2);
        testSessionEditor(editors[2], SessionCreateTest.TEST_FILE_3);
    }
