package org.eclipse.e4.ui.progress.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.progress.IProgressConstants;
import org.eclipse.e4.ui.progress.UIJob;
import org.eclipse.e4.ui.progress.internal.legacy.PlatformUI;

@Creatable
@Singleton
public class ProgressViewUpdater implements IJobProgressManagerListener {

    private IProgressUpdateCollector[] collectors;

    Job updateJob;

    UpdatesInfo currentInfo = new UpdatesInfo();

    Object updateLock = new Object();
    
    @Inject
    ProgressManager progressManager;

	class MutableBoolean {
		boolean value;
	}

	MutableBoolean updateScheduled = new MutableBoolean();

   
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

    ProgressViewUpdater() {
        createUpdateJob();
        collectors = new IProgressUpdateCollector[0];
    }
    
    @PostConstruct
    void init(MApplication application) {
    	progressManager.addListener(this);
    	application.getContext().set(ProgressViewUpdater.class, this);
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
        	progressManager.removeListener(this);
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
        updateJob = new UIJob(ProgressMessages.ProgressContentProvider_UpdateProgressJob) {
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

    public void refreshJobInfo(JobInfo info) {

        if (isUpdateJob(info.getJob())) {
			return;
		}

        synchronized (updateLock) {
            currentInfo.refresh(info);
        }
        scheduleUpdate();

    }

    public void refreshGroup(GroupInfo info) {
        synchronized (updateLock) {
            currentInfo.refresh(info);
        }
        scheduleUpdate();

    }

    public void addGroup(GroupInfo info) {

        synchronized (updateLock) {
            currentInfo.add(info);
        }
        scheduleUpdate();

    }

    public void refreshAll() {

        synchronized (updateLock) {
            currentInfo.updateAll = true;
        }

        scheduleUpdate();

    }

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

    public void removeGroup(GroupInfo group) {
        synchronized (updateLock) {
            currentInfo.remove(group);
        }
        scheduleUpdate();

    }

    public boolean showsDebug() {
    	return Preferences.getBoolean(IProgressConstants.SHOW_SYSTEM_JOBS);
    }

    boolean isUpdateJob(Job job) {
        return job.equals(updateJob);
    }
}
