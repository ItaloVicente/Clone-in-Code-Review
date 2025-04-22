
package org.eclipse.ui.internal.progress;

import org.eclipse.core.runtime.jobs.Job;

interface IJobBusyListener {

    public void incrementBusy(Job job);

    public void decrementBusy(Job job);

}
