        /**
         * First tab is Name
         */
        assertEquals("Name", tabDescriptors[0].getLabel());//$NON-NLS-1$
        /**
         * Second tab is Warning
         */
        assertEquals("Warning", tabDescriptors[1].getLabel());//$NON-NLS-1$
        /**
         * Third tab is Message
         */
        assertEquals("Message", tabDescriptors[2].getLabel());//$NON-NLS-1$
        /**
         * No fourth tab
         */
        assertEquals(3, tabDescriptors.length);
