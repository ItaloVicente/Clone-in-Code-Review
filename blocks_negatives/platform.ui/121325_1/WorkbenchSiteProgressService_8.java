        void clearCursors() {
            if (waitCursor != null) {
                waitCursor.dispose();
                waitCursor = null;
            }
        }

