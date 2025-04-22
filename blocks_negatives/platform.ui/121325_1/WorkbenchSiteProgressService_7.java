        /**
         * Get the wait cursor. Initialize it if required.
         * @param display the display to create the cursor on.
         * @return the created cursor
         */
        private Cursor getWaitCursor(Display display) {
            if (waitCursor == null) {
                waitCursor = new Cursor(display, SWT.CURSOR_APPSTARTING);
            }
            return waitCursor;
        }

