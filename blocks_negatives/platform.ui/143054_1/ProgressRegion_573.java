                if (element instanceof JobInfo) {
                    JobInfo info= (JobInfo)element;
                    if (info.isBlocked() || info.getJob().getState() == Job.WAITING) {
                    	return false;
                    }
                }
                return true;
            }
