    public void testWorkingSetRegistry() throws Throwable {
        WorkingSetDescriptor[] workingSetDescriptors = fRegistry
                .getWorkingSetDescriptors();
        /*
         * Should have at least resourceWorkingSetPage and MockWorkingSet
         */
        assertTrue(workingSetDescriptors.length >= 2);
