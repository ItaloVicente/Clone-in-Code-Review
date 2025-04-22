            if (zombie) {
                hasZombieWritten = true;
            } else {
                hasThresholdWritten = true;
            }
        }

        /**
         * Logs the zombie data and resets the sets.
         */
        private void prepareAndLogZombies() {
            if (!hasZombieWritten) {
                return;
            }
            hasZombieWritten = false;

            List<Map<String, Object>> output = new ArrayList<Map<String, Object>>();

            if (!kvZombieSet.isEmpty()) {
                output.add(convertThresholdSet(kvZombieSet, kvZombieCount, SERVICE_KV));
                kvZombieSet.clear();
                kvZombieCount = 0;
            }
            if (!n1qlZombieSet.isEmpty()) {
                output.add(convertThresholdSet(n1qlZombieSet, n1qlZombieCount, SERVICE_N1QL));
                n1qlZombieSet.clear();
                n1qlZombieCount = 0;
            }
            if (!viewZombieSet.isEmpty()) {
                output.add(convertThresholdSet(viewZombieSet, viewZombieCount, SERVICE_VIEW));
                viewZombieSet.clear();
                viewZombieCount = 0;
            }
            if (!ftsZombieSet.isEmpty()) {
                output.add(convertThresholdSet(ftsZombieSet, ftsZombieCount, SERVICE_FTS));
                ftsZombieSet.clear();
                ftsZombieCount = 0;
            }
            if (!analyticsZombieSet.isEmpty()) {
                output.add(convertThresholdSet(analyticsZombieSet, analyticsZombieCount, SERVICE_ANALYTICS));
                analyticsZombieSet.clear();
                analyticsZombieCount = 0;
            }

            logZombies(output);
