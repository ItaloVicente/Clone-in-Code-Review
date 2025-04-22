                superMonitor.beginTask(name, totalWork);
                checkTicking();
            }

            /**
             * Check if we have ticked in the last 800ms.
             */
            private void checkTicking() {
                if (watchTime < 0) {
