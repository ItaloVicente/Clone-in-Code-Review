package org.eclipse.jgit.junit;

public class Stats {
	private int n = 0;

	private double avg = 0.0;

	private double min = 0.0;

	private double max = 0.0;

	private double sum = 0.0;

	public void add(double x) {
		n++;
		min = n == 1 ? x : Math.min(min
		max = n == 1 ? x : Math.max(max
		double d = x - avg;
		avg += d / n;
		sum += d * d * (n - 1) / n;
	}

	public int count() {
		return n;
	}

	public double min() {
		return min;
	}

	public double max() {
		return max;
	}


	public double avg() {
		return avg;
	}

	public double var() {
		if (n < 2) {
			return Double.NaN;
		}
		return sum / (n - 1);
	}

	public double stddev() {
		return Math.sqrt(this.var());
	}
}
