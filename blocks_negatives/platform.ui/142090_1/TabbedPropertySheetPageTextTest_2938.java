        /**
         * First tab is "This"
         */
        assertEquals("This", tabDescriptors[0].getLabel());//$NON-NLS-1$
        /**
         * Second tab is "is"
         */
        assertEquals("is", tabDescriptors[1].getLabel());//$NON-NLS-1$
        /**
         * Third tab is "a"
         */
        assertEquals("a", tabDescriptors[2].getLabel());//$NON-NLS-1$
        /**
         * Third tab is "test"
         */
        assertEquals("test", tabDescriptors[3].getLabel());//$NON-NLS-1$
        /**
         * No fifth tab
         */
        assertEquals(4, tabDescriptors.length);
    }
