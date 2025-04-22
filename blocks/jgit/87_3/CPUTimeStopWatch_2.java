
package org.eclipse.jgit.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CPUTimeStopWatch {
	long start = 0;

	private ThreadMXBean mxBean;

	private boolean cpuTimeSupported;

	public CPUTimeStopWatch(boolean checkForCPUTimeSupport) {
		mxBean = ManagementFactory.getThreadMXBean();
		cpuTimeSupported = mxBean.isCurrentThreadCpuTimeSupported();
		if (checkForCPUTimeSupport) {
			throw new UnsupportedOperationException(
					"This VM doesn't support getting CPU-time info");
		}
	}

	public boolean start() {
		start = cpuTimeSupported ? mxBean.getCurrentThreadCpuTime() : 0;
		return cpuTimeSupported;
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
