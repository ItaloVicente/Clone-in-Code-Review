
		public String toConfigValue() {
			return "--" + name().toLowerCase().replace('_'
		}

		public boolean matchConfigValue(String in) {
			if (StringUtils.isEmptyOrNull(in))
				return false;
				return false;
			return name().equalsIgnoreCase(in.substring(2).replace('-'
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
