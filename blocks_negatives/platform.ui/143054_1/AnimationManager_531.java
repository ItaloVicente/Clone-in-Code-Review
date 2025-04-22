                jobs.clear();
                setAnimated(false);
                JobInfo[] currentInfos = progressManager.getJobInfos(showsDebug());
                for (JobInfo currentInfo : currentInfos) {
                    addJob(currentInfo);
                }
            }

            @Override
