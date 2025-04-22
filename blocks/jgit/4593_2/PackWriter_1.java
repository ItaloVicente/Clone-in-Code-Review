
	private class MutableState {
		private volatile PackingPhase phase;

		MutableState() {
			phase = PackingPhase.COUNTING;
		}

		State snapshot() {
			return new State(phase);
		}
	}

	public static enum PackingPhase {
		COUNTING

		GETTING_SIZES

		FINDING_SOURCES

		COMPRESSING

		WRITING;
	}

	public class State {
		private final PackingPhase phase;

		State(PackingPhase phase) {
			this.phase = phase;
		}

		public PackConfig getConfig() {
			return config;
		}

		public PackingPhase getPhase() {
			return phase;
		}

		@Override
		public String toString() {
			return "PackWriter.State[" + phase + "]";
		}
	}
