                if (current != null && !current.isDisposed()) {
                    enabled[i] = current.getEnabled();
                    current.setEnabled(false);
                }
            }

            Control currentFocus = display.getFocusControl();
            try {
                contents.setEnabled(false);
                if (menuBar != null) {
