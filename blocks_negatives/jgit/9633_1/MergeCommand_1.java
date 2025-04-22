
		/**
		 * The modes available for fast forward merges corresponding to the
		 * options under branch.<name>.branch config option.
		 */
		public enum MergeOptions {
			/**
			 * {@link FastForwardMode#FF}.
			 */
			__FF,
			/**
			 * {@link FastForwardMode#NO_FF}.
			 */
			__NO_FF,
			/**
			 * {@link FastForwardMode#FF_ONLY}.
			 */
			__FF_ONLY;

			/**
			 * Map from <code>FastForwardMode</code> to
			 * <code>FastForwardMode.MergeOptions</code>.
			 *
			 * @param ffMode
			 *            the <code>FastForwardMode</code> value to be mapped
			 * @return the mapped code>FastForwardMode.MergeOptions</code> value
			 */
			public static MergeOptions valueOf(FastForwardMode ffMode) {
				switch (ffMode) {
				case NO_FF:
					return __NO_FF;
				case FF_ONLY:
					return __FF_ONLY;
				default:
					return __FF;
				}
			}
		}

		/**
		 * The modes available for fast forward merges corresponding to the
		 * options under merge.ff config option.
		 */
		public enum Merge {
			/**
			 * {@link FastForwardMode#FF}.
			 */
			TRUE,
			/**
			 * {@link FastForwardMode#NO_FF}.
			 */
			FALSE,
			/**
			 * {@link FastForwardMode#FF_ONLY}.
			 */
			ONLY;

			/**
			 * Map from <code>FastForwardMode</code> to
			 * <code>FastForwardMode.Merge</code>.
			 *
			 * @param ffMode
			 *            the <code>FastForwardMode</code> value to be mapped
			 * @return the mapped code>FastForwardMode.Merge</code> value
			 */
			public static Merge valueOf(FastForwardMode ffMode) {
				switch (ffMode) {
				case NO_FF:
					return FALSE;
				case FF_ONLY:
					return ONLY;
				default:
					return TRUE;
				}
			}
		}

		/**
		 * Map from <code>FastForwardMode.Merge</code> to
		 * <code>FastForwardMode</code>.
		 *
		 * @param ffMode
		 *            the <code>FastForwardMode.Merge</code> value to be mapped
		 * @return the mapped code>FastForwardMode</code> value
		 */
		public static FastForwardMode valueOf(FastForwardMode.Merge ffMode) {
			switch (ffMode) {
			case FALSE:
				return NO_FF;
			case ONLY:
				return FF_ONLY;
			default:
				return FF;
			}
		}

		/**
		 * Map from <code>FastForwardMode.MergeOptions</code> to
		 * <code>FastForwardMode</code>.
		 *
		 * @param ffMode
		 *            the <code>FastForwardMode.MergeOptions</code> value to be
		 *            mapped
		 * @return the mapped code>FastForwardMode</code> value
		 */
		public static FastForwardMode valueOf(
				FastForwardMode.MergeOptions ffMode) {
			switch (ffMode) {
			case __NO_FF:
				return NO_FF;
			case __FF_ONLY:
				return FF_ONLY;
			default:
				return FF;
			}
		}
