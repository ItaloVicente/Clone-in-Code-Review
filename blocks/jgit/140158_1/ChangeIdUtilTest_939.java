
package org.eclipse.jgit.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CPUTimeStopWatch {
	private long start;

	private static ThreadMXBean mxBean=ManagementFactory.getThreadMXBean();

	public static CPUTimeStopWatch createInstance() {
		return mxBean.isCurrentThreadCpuTimeSupported() ? new CPUTimeStopWatch()
				: null;
	}

	public void start() {
		start = mxBean.getCurrentThreadCpuTime();
	}

	public long stop() {
		long cpuTime = readout();
		start = 0;
		return cpuTime;
	}

	public long readout() {
		return (start == 0) ? 0 : mxBean.getCurrentThreadCpuTime() - start;
	}
}
