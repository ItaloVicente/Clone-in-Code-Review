                jobs.add(info.getJob());
            }

            /*
             * Decrement the job count for the job
             */
            private void decrementJobCount(Job job) {
                jobs.remove(job);
                if (jobs.isEmpty()) {
