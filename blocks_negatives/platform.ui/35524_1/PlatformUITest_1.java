    public void testCreateAndRunWorkbenchWithExceptionOnStartup() {
        final Display display = PlatformUI.createDisplay();
        assertNotNull(display);

        WorkbenchAdvisorObserver wa = new WorkbenchAdvisorObserver(1) {
            public void preStartup() {
                throw new IllegalArgumentException("Thrown deliberately by PlatformUITest");
            }
        };

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_UNSTARTABLE, code);
        assertFalse(PlatformUI.isWorkbenchRunning());
        display.dispose();
        assertTrue(display.isDisposed());
