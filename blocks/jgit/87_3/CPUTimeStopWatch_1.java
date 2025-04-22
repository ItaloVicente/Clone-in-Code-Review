
package org.eclipse.jgit.diff;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jgit.util.CPUTimeStopWatch;

public class MyersDiffPerformanceTest extends TestCase {
	private static final long longTaskBoundary = 5000000000L;

	private static final int minCPUTimerTicks = 10;

	private static final int maxFactor = 15;

	static List<PerfData> perfData = new LinkedList<PerfData>();

	static CPUTimeStopWatch stopwatch = new CPUTimeStopWatch(false);

	public class PerfData {
		private NumberFormat fmt = new DecimalFormat("#.##E0");

		public long runningTime;

		public long D;

		public long N;

		private double p1 = -1;

		private double p2 = -1;

		public double perf1() {
			if (p1 < 0)
				p1 = runningTime / ((double) N * D);
			return p1;
		}

		public double perf2() {
			if (p2 < 0)
				p2 = runningTime / ((double) N * D * D);
			return p2;
		}

		public String toString() {
			return ("diffing " + N / 2 + " bytes took " + runningTime
					+ " ns. N=" + N + "
					+ fmt.format(perf1()) + "
					.format(perf2()));
		}
	}

	public static Comparator<PerfData> getComparator(final int whichPerf) {
		return new Comparator<PerfData>() {
			public int compare(PerfData o1
				double p1 = (whichPerf == 1) ? o1.perf1() : o1.perf2();
				double p2 = (whichPerf == 1) ? o2.perf1() : o2.perf2();
				return (p1 < p2) ? -1 : (p1 > p2) ? 1 : 0;
			}
		};
	}

	public void test() {
		perfData.add(test(10000));
		perfData.add(test(20000));
		perfData.add(test(50000));
		perfData.add(test(80000));
		perfData.add(test(99999));
		perfData.add(test(999999));

		Comparator<PerfData> c = getComparator(1);
		double factor = Collections.max(perfData
				/ Collections.min(perfData
		assertTrue(
				"minimun and maximum of performance-index t/(N*D) differed too much. Measured factor of "
						+ factor
						+ " (maxFactor="
						+ maxFactor
						+ "). Perfdata=<"
						+ perfData.toString() + ">"
	}

	private PerfData test(int characters) {
		PerfData ret = new PerfData();
		String a = DiffTestDataGenerator.generateSequence(characters
		String b = DiffTestDataGenerator.generateSequence(characters
		CharArray ac = new CharArray(a);
		CharArray bc = new CharArray(b);
		MyersDiff myersDiff = null;
		int cpuTimeChanges = 0;
		long lastReadout = 0;
		long interimTime = 0;
		int repetitions = 0;
		stopwatch.start();
		while (cpuTimeChanges < minCPUTimerTicks && interimTime < longTaskBoundary) {
			myersDiff = new MyersDiff(ac
			repetitions++;
			interimTime = stopwatch.readout();
			if (interimTime != lastReadout) {
				cpuTimeChanges++;
				lastReadout = interimTime;
			}
		}
		ret.runningTime = stopwatch.stop() / repetitions;
		ret.N = (ac.size() + bc.size());
		ret.D = myersDiff.getEdits().size();
		
		return ret;
	}

	private static class CharArray implements Sequence {
		private final char[] array;

		public CharArray(String s) {
			array = s.toCharArray();
		}

		public int size() {
			return array.length;
		}

		public boolean equals(int i
			CharArray o = (CharArray) other;
			return array[i] == o.array[j];
		}
	}
}
