
package org.eclipse.jgit.storage.dht;

import java.util.concurrent.TimeUnit;

public class Timeout {
	public static Timeout milliseconds(int millis) {
		return new Timeout(millis
	}

	public static Timeout seconds(int sec) {
		return new Timeout(sec
	}

	public static Timeout seconds(double sec) {
		return new Timeout((long) (sec * 1000)
	}

	private final long time;

	private final TimeUnit unit;

	public Timeout(long time
		this.time = time;
		this.unit = unit;
	}

	public long getTime() {
		return time;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	@Override
	public int hashCode() {
		return (int) time;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Timeout)
			return getTime() == ((Timeout) other).getTime()
					&& getUnit().equals(((Timeout) other).getUnit());
		return false;
	}

	@Override
	public String toString() {
		return getTime() + " " + getUnit();
	}
}
