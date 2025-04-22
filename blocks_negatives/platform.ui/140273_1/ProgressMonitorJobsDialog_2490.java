                if ((System.currentTimeMillis() - watchTime) > ProgressManager
                        .getInstance().getLongOperationTime()) {
                    watchTime = -1;
                    openDialog();
                }
            }

            /**
             * Open the dialog in the ui Thread
             */
            private void openDialog() {
                if (!PlatformUI.isWorkbenchRunning()) {
