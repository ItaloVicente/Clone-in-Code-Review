                if ((System.currentTimeMillis() - watchTime) > progressService.getLongOperationTime()) {
                    watchTime = -1;
                    openDialog();
                }
            }

            /**
             * Open the dialog in the ui Thread
             */
            private void openDialog() {
                if (!PlatformUI.isWorkbenchRunning()) {
