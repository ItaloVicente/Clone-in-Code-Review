
package org.eclipse.ui.tests.progress;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.internal.jobs.InternalJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.internal.progress.JobInfo;

public class JobInfoTestOrdering extends TestCase {

	private List jobinfos = new ArrayList();
	
	@Override
	protected void setUp() throws Exception {
		jobinfos.clear();
		int counter = 0;
		TestJob job;
		JobInfo ji;
		
		job = new TestJob("Job" + (counter++));
		job.setUser(true);
		job.setSystem(false);
		job.setPriority(Job.INTERACTIVE);
		job.setInternalJobState(Job.NONE);  // JOB STATE
		ji = new ExtendedJobInfo(job);
		jobinfos.add(ji);
		
		job = new TestJob("Job" + (counter++));
		job.setUser(true);
		job.setSystem(false);
		job.setPriority(Job.INTERACTIVE);
		job.setInternalJobState(Job.SLEEPING);  // JOB STATE
		ji = new ExtendedJobInfo(job);
		jobinfos.add(ji);
		
		job = new TestJob("Job" + (counter++));
		job.setUser(true);
		job.setSystem(false);
		job.setPriority(Job.INTERACTIVE);
		job.setInternalJobState(Job.WAITING);  // JOB STATE
		ji = new ExtendedJobInfo(job);
		jobinfos.add(ji);
		
		job = new TestJob("Job" + (counter++));
		job.setUser(true);
		job.setSystem(false);
		job.setPriority(Job.INTERACTIVE);
		job.setInternalJobState(Job.RUNNING);  // JOB STATE
		ji = new ExtendedJobInfo(job);
		jobinfos.add(ji);
		
	}

	public void testJobStateOrdering() {
		Collections.sort(jobinfos);
		assertEquals(Job.RUNNING,  ((JobInfo)jobinfos.get(0)).getJob().getState());
		assertEquals(Job.WAITING,  ((JobInfo)jobinfos.get(1)).getJob().getState());
		assertEquals(Job.SLEEPING, ((JobInfo)jobinfos.get(2)).getJob().getState());
		assertEquals(Job.NONE,     ((JobInfo)jobinfos.get(3)).getJob().getState());
	}
	

	private static class ExtendedJobInfo extends JobInfo {

		public ExtendedJobInfo(Job enclosingJob) {
			super(enclosingJob);
		}

		@Override
		public String toString() {
			return "ExtendedJobInfo [getName()=" + getJob().getName() + ", getPriority()="
						+ getJob().getPriority() + ", getState()=" + getJob().getState()
						+ ", isSystem()=" + getJob().isSystem() + ", isUser()=" + getJob().isUser()
						+ "]";
		}
		
	}
	
	private static class TestJob extends Job {

		public TestJob(String name) {
			super(name);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			throw new UnsupportedOperationException("Not implemented, because of just a unit test");
		}
		
		public void setInternalJobState(int state) {
			try {
				final Field field = InternalJob.class.getDeclaredField("flags");
				field.setAccessible(true); // hack for testing
				field.set(this, new Integer(state));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}
}
