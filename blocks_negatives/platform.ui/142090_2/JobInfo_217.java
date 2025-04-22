            return (int) info.preWork * 100 / info.totalWork;
        }
        return IProgressMonitor.UNKNOWN;
    }

    /**
     * @return Returns the taskInfo.
     */
    TaskInfo getTaskInfo() {
        return taskInfo;
    }

    @Override
