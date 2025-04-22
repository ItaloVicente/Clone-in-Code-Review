            }

            /**
             * If this is one of our jobs or not running then don't bother.
             */
            private boolean isNotTracked(JobInfo info) {
                Job job = info.getJob();
                return job.getState() != Job.RUNNING
                        || animationProcessor.isProcessorJob(job);
            }

            @Override
