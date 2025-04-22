package org.eclipse.ui.internal.progress;

interface IJobProgressManagerListener {

    void addJob(final JobInfo info);

    void addGroup(final GroupInfo info);

    public void refreshJobInfo(JobInfo info);

    public void refreshGroup(GroupInfo info);

    void refreshAll();

    void removeJob(final JobInfo info);

    void removeGroup(final GroupInfo group);

    boolean showsDebug();
}
