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
                output.add(convertThresholdSet(kvZombieSet, SERVICE_KV));
                kvZombieSet.clear();
            }
            if (!n1qlZombieSet.isEmpty()) {
                output.add(convertThresholdSet(n1qlZombieSet, SERVICE_N1QL));
                n1qlZombieSet.clear();
            }
            if (!viewZombieSet.isEmpty()) {
                output.add(convertThresholdSet(viewZombieSet, SERVICE_VIEW));
                viewZombieSet.clear();
            }
            if (!ftsZombieSet.isEmpty()) {
                output.add(convertThresholdSet(ftsZombieSet, SERVICE_FTS));
                ftsZombieSet.clear();
            }
            if (!analyticsZombieSet.isEmpty()) {
                output.add(convertThresholdSet(analyticsZombieSet, SERVICE_ANALYTICS));
                analyticsZombieSet.clear();
            }

            logZombies(output);
