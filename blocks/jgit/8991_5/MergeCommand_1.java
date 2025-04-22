
		public enum MergeOptions {
			__FF
			__NO_FF
			__FF_ONLY;

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

		public enum Merge {
			TRUE
			FALSE
			ONLY;

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
