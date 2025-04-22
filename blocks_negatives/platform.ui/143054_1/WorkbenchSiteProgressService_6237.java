        /**
         * Set whether we are updating with the wait or busy cursor.
         *
         * @param cursorState
         */
        void setBusy(boolean cursorState) {
            synchronized (lock) {
                busy = cursorState;
            }
        }
