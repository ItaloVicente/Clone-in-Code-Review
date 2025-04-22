package org.eclipse.ui.internal.progress;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.progress.WorkbenchJob;

class ProgressViewUpdater implements IJobProgressManagerListener {

    private static ProgressViewUpdater singleton;

    private IProgressUpdateCollector[] collectors;

    Job updateJob;

    UpdatesInfo currentInfo = new UpdatesInfo();

    Object updateLock = new Object();

	class MutableBoolean {
		boolean value;
	}

	MutableBoolean updateScheduled = new MutableBoolean();

    boolean debug;
    
   
    class UpdatesInfo {

        Collection additions = new HashSet();

        Collection deletions = new HashSet();

        Collection refreshes = new HashSet();

        boolean updateAll = false;

        private UpdatesInfo() {
        }

        void add(JobTreeElement addition) {
            additions.add(addition);
        }

        void remove(JobTreeElement removal) {
            deletions.add(removal);
        }

        void refresh(JobTreeElement refresh) {
            refreshes.add(refresh);
        }

        void reset() {
            additions.clear();
            deletions.clear();
            refreshes.clear();
            updateAll = false;
        }

        void processForUpdate() {
            HashSet staleAdditions = new HashSet();

            Iterator additionsIterator = additions.iterator();
            while (additionsIterator.hasNext()) {
                JobTreeElement treeElement = (JobTreeElement) additionsIterator
                        .next();
                if (!treeElement.isActive()) {
                    if (deletions.contains(treeElement)) {
						staleAdditions.add(treeElement);
					}
                }
            }

            additions.removeAll(staleAdditions);

            HashSet obsoleteRefresh = new HashSet();
            Iterator refreshIterator = refreshes.iterator();
            while (refreshIterator.hasNext()) {
                JobTreeElement treeElement = (JobTreeElement) refreshIterator
                        .next();
                if (deletions.contains(treeElement)
                        || additions.contains(treeElement)) {
					obsoleteRefresh.add(treeElement);
				}
                
               Object parent = treeElement.getParent();
               if(parent != null && (deletions.contains(parent)
                       || additions.contains(parent))){
            	   obsoleteRefresh.add(treeElement);
               }
               
                if (!treeElement.isActive()) {
                    obsoleteRefresh.add(treeElement);
                    deletions.add(treeElement);
                }
            }

            refreshes.removeAll(obsoleteRefresh);

        }
    }

   static ProgressViewUpdater getSingleton() {
        if (singleton == null) {
			singleton = new ProgressViewUpdater();
		}
        return singleton;
    }

    static boolean hasSingleton() {
        return singleton != null;
    }

    static void clearSingleton() {
        if (singleton != null) {
			ProgressManager.getInstance().removeListener(singleton);
		}
        singleton = null;
    }

    private ProgressViewUpdater() {
        createUpdateJob();
        collectors = new IProgressUpdateCollector[0];
        ProgressManager.getInstance().addListener(this);
        debug = 
        	PrefUtil.getAPIPreferenceStore().
        		getBoolean(IWorkbenchPreferenceConstants.SHOW_SYSTEM_JOBS);
    }

    void addCollector(IProgressUpdateCollector newCollector) {
        IProgressUpdateCollector[] newCollectors = new IProgressUpdateCollector[collectors.length + 1];
        System.arraycopy(collectors, 0, newCollectors, 0, collectors.length);
        newCollectors[collectors.length] = newCollector;
        collectors = newCollectors;
    }

    void removeCollector(IProgressUpdateCollector provider) {
        HashSet newCollectors = new HashSet();
        for (int i = 0; i < collectors.length; i++) {
            if (!collectors[i].equals(provider)) {
				newCollectors.add(collectors[i]);
			}
        }
        IProgressUpdateCollector[] newArray = new IProgressUpdateCollector[newCollectors
                .size()];
        newCollectors.toArray(newArray);
        collectors = newArray;
        if (collectors.length == 0) {
			clearSingleton();
		}
    }

    void scheduleUpdate() {
        if (PlatformUI.isWorkbenchRunning()) {
			boolean scheduleUpdate = false;
			synchronized (updateScheduled) {
				if (!updateScheduled.value || updateJob.getState() == Job.NONE) {
					updateScheduled.value = scheduleUpdate = true;
				}
        	}
			if (scheduleUpdate)
				updateJob.schedule(100);
        }
    }

    private void createUpdateJob() {
        updateJob = new WorkbenchJob(ProgressMessages.ProgressContentProvider_UpdateProgressJob) {
            @Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				synchronized (updateScheduled) {
					updateScheduled.value = false;
				}
				if (collectors.length == 0) {
					return Status.CANCEL_STATUS;
				}

				if (currentInfo.updateAll) {
					synchronized (updateLock) {
						currentInfo.reset();
					}
					for (int i = 0; i < collectors.length; i++) {
						collectors[i].refresh();
					}

				} else {
					Object[] updateItems;
					Object[] additionItems;
					Object[] deletionItems;
					synchronized (updateLock) {
						currentInfo.processForUpdate();

						updateItems = currentInfo.refreshes.toArray();
						additionItems = currentInfo.additions.toArray();
						deletionItems = currentInfo.deletions.toArray();

						currentInfo.reset();
					}

					for (int v = 0; v < collectors.length; v++) {
						IProgressUpdateCollector collector = collectors[v];

						if (updateItems.length > 0) {
							collector.refresh(updateItems);
						}
						if (additionItems.length > 0) {
							collector.add(additionItems);
						}
						if (deletionItems.length > 0) {
							collector.remove(deletionItems);
						}
					}
				}

				return Status.OK_STATUS;
			}

			@Override
			protected void canceling() {
				synchronized (updateScheduled) {
					updateScheduled.value = false;
				}
            }
        };
        updateJob.setSystem(true);
        updateJob.setPriority(Job.DECORATE);
        updateJob.setProperty(ProgressManagerUtil.INFRASTRUCTURE_PROPERTY, new Object());

    }

    UpdatesInfo getCurrentInfo() {
        return currentInfo;
    }

    public void refresh(JobInfo info) {

        if (isUpdateJob(info.getJob())) {
			return;
		}

        synchronized (updateLock) {
            currentInfo.refresh(info);
            GroupInfo group = info.getGroupInfo();
            if (group != null) {
				currentInfo.refresh(group);
			}
        }
        scheduleUpdate();

    }

    @Override
	public void refreshJobInfo(JobInfo info) {

        if (isUpdateJob(info.getJob())) {
			return;
		}

        synchronized (updateLock) {
            currentInfo.refresh(info);
        }
        scheduleUpdate();

    }

    @Override
	public void refreshGroup(GroupInfo info) {
        synchronized (updateLock) {
            currentInfo.refresh(info);
        }
        scheduleUpdate();

    }

    @Override
	public void addGroup(GroupInfo info) {

        synchronized (updateLock) {
            currentInfo.add(info);
        }
        scheduleUpdate();

    }

    @Override
	public void refreshAll() {

        synchronized (updateLock) {
            currentInfo.updateAll = true;
        }

        scheduleUpdate();

    }

    @Override
	public void addJob(JobInfo info) {

        if (isUpdateJob(info.getJob())) {
			return;
		}

        synchronized (updateLock) {
            GroupInfo group = info.getGroupInfo();

            if (group == null) {
				currentInfo.add(info);
			} else {
                currentInfo.refresh(group);
            }
        }
        scheduleUpdate();

    }

    @Override
	public void removeJob(JobInfo info) {

        if (isUpdateJob(info.getJob())) {
			return;
		}

        synchronized (updateLock) {
            GroupInfo group = info.getGroupInfo();
            if (group == null) {
				currentInfo.remove(info);
			} else {
                currentInfo.refresh(group);
            }
        }
        scheduleUpdate();
    }

    @Override
	public void removeGroup(GroupInfo group) {
        synchronized (updateLock) {
            currentInfo.remove(group);
        }
        scheduleUpdate();

    }

    @Override
	public boolean showsDebug() {
        return debug;
    }

    boolean isUpdateJob(Job job) {
        return job.equals(updateJob);
    }
}
