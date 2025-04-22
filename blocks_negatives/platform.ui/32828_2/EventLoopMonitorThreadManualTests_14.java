			/**
			 * Replaces the super-class implementation with a no-op. This breaks the implicit contract
			 * that some amount of time should have passed when sleepForMillis is called with a non-zero
			 * argument, but in this testing environment giving the unit tests complete control over the
			 * elapsed time allows the tests to be more deterministic.
			 */
