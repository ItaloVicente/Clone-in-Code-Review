package com.couchbase.client.java.options;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class CounterOptions {
	private long initial;
	private long delta;
	private int expiry;

	private CounterOptions(final long initial, final long delta, int expiry) {
		this.initial = initial;
		this.delta = delta;
		this.expiry = expiry;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private long initial;
		private long delta;
		private int expiry;

		public Builder initial(long initial) {
			this.initial = initial;
			return this;
		}

		public Builder delta(long delta) {
			this.delta = delta;
			return this;
		}

		public Builder expiry(int expiry) {
			this.expiry = expiry;
			return this;
		}

		public CounterOptions build() {
			return new CounterOptions(initial, delta, expiry);
		}
	}

	public long initial() { return this.initial; }

	public long delta() { return this.delta; }

	public int expiry() { return this.expiry; }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CounterOptions{");
		sb.append("initial:" + this.initial + ",");
		sb.append("delta:" + this.delta + "}");
		sb.append("expiry:" + this.expiry + "}");
		return sb.toString();
	}
}
