
package org.eclipse.e4.ui.progress.internal;

import org.eclipse.core.runtime.jobs.Job;

interface IJobBusyListener {

    public void incrementBusy(Job job);

    public void decrementBusy(Job job);

}
