
package org.eclipse.ui.tests.progress;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.internal.jobs.InternalJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.internal.progress.JobInfo;

public class JobInfoTest extends TestCase {

	
	static final int ABOUT_TO_RUN = 0x10;
	static final int ABOUT_TO_SCHEDULE = 0x20;
	static final int BLOCKED = 0x08;
	static final int YIELDING = 0x40;
	
	private List jobinfos = new ArrayList();
	
	@Override
	protected void setUp() throws Exception {
		int counter = 0;
		counter = createAndAddJobInfos(false, false, ABOUT_TO_RUN, counter);
		counter = createAndAddJobInfos(false, true,  ABOUT_TO_RUN, counter);
		counter = createAndAddJobInfos(true, false,  ABOUT_TO_RUN, counter);
		counter = createAndAddJobInfos(true, true,  ABOUT_TO_RUN, counter);
		
		counter = createAndAddJobInfos(false, false, ABOUT_TO_SCHEDULE, counter);
		counter = createAndAddJobInfos(false, true, ABOUT_TO_SCHEDULE, counter);
		counter = createAndAddJobInfos(true, false, ABOUT_TO_SCHEDULE, counter);
		counter = createAndAddJobInfos(true, true, ABOUT_TO_SCHEDULE, counter);

		counter = createAndAddJobInfos(false, false, Job.SLEEPING, counter);
		counter = createAndAddJobInfos(false, true, Job.SLEEPING, counter);
		counter = createAndAddJobInfos(true, false, Job.SLEEPING, counter);
		counter = createAndAddJobInfos(true, true, Job.SLEEPING, counter);
		
		counter = createAndAddJobInfos(false, false, Job.WAITING, counter);
		counter = createAndAddJobInfos(false, true, Job.WAITING, counter);
		counter = createAndAddJobInfos(true, false, Job.WAITING, counter);
		counter = createAndAddJobInfos(true, true, Job.WAITING, counter);
		
		counter = createAndAddJobInfos(false, false, Job.RUNNING, counter);
		counter = createAndAddJobInfos(false, true, Job.RUNNING, counter);
		counter = createAndAddJobInfos(true, false, Job.RUNNING, counter);
		counter = createAndAddJobInfos(true, true, Job.RUNNING, counter);
		
	}

	public void testCompareToContractCompliance() {
		for(int xi = 0; xi<this.jobinfos.size(); xi++) {
			JobInfo x = (JobInfo) jobinfos.get(xi);

			for(int yi = 0; yi<this.jobinfos.size(); yi++) {
				JobInfo y = (JobInfo) jobinfos.get(yi);
				int xyResult = x.compareTo(y);
				int yxResult = y.compareTo(x);
				assertEquals(String.format("sgn(compare(%1$s, %2$s)) != -sgn(compare(%2$s, %1$s))", new Object[] { x, y}),
						Math.round(Math.signum(xyResult)) , Math.round(-Math.signum(yxResult)));

				for(int zi = 0; zi<this.jobinfos.size(); zi++) {
					JobInfo z = (JobInfo) jobinfos.get(zi);
					int xzResult = x.compareTo(z);
					int yzResult = y.compareTo(z);
					if(xyResult > 0) {
						if(yzResult > 0) {
							assertTrue(String.format("((compare(%1$s, %2$s)>0) && (compare(%2$s, %3$s)>0)) but not compare(%1$s, %3$s)>0", new Object[] {x, y, z}),
									xzResult > 0);
						}
					}
					else if(xyResult == 0) {
						assertEquals(String.format("compare(%1$s, %2$s)==0 but not that sgn(compare(%1$s, %3$s))==sgn(compare(%2$s, %3$s))", new Object[]{ x, y, z}),
								Math.round(Math.signum(xzResult)) , Math.round(Math.signum(yzResult)));
					}
				}

				boolean consistentWithEquals = true;
				if(consistentWithEquals && xyResult == 0) {
					assertTrue(String.format("compare(%1$s, %2$s)==0) == (%1$s.equals(%2$s)", new Object[] {x, y}),
							x.equals(y));
				}
			}
		}
	}
	
	private int createAndAddJobInfos(boolean user, boolean system, int jobstate, int counter) {
		TestJob job;
		JobInfo ji;
		
		job = new TestJob("Job" + (counter++));
		job.setUser(user);
		job.setSystem(system);
		job.setPriority(Job.INTERACTIVE);
		job.setInternalJobState(jobstate);
		ji = new ExtendedJobInfo(job);
		jobinfos.add(ji);
		
		job = new TestJob("Job" + (counter++));
		job.setUser(user);
		job.setSystem(system);
		job.setPriority(Job.SHORT);
		job.setInternalJobState(jobstate);
		ji = new ExtendedJobInfo(job);
		jobinfos.add(ji);
		
		job = new TestJob("Job" + (counter++));
		job.setUser(user);
		job.setSystem(system);
		job.setPriority(Job.LONG);
		job.setInternalJobState(jobstate);
		ji = new ExtendedJobInfo(job);
		jobinfos.add(ji);
		
		job = new TestJob("Job" + (counter++));
		job.setUser(user);
		job.setSystem(system);
		job.setPriority(Job.BUILD);
		job.setInternalJobState(jobstate);
		ji = new ExtendedJobInfo(job);
		jobinfos.add(ji);
		
		job = new TestJob("Job" + (counter++));
		job.setUser(user);
		job.setSystem(system);
		job.setPriority(Job.DECORATE);
		job.setInternalJobState(jobstate);
		ji = new ExtendedJobInfo(job);
		jobinfos.add(ji);
		
		job = new TestJob("Job" + (counter++));
		job.setUser(user);
		job.setSystem(system);
		job.setPriority(Job.LONG);
		job.setInternalJobState(jobstate);
		ji = new ExtendedJobInfo(job);
		jobinfos.add(ji);
		
		return counter;
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
