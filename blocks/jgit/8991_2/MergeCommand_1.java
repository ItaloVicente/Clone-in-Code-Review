
		public String toString(String section
			if (ConfigConstants.CONFIG_BRANCH_SECTION.equals(section)
					&& subsection != null
					&& ConfigConstants.CONFIG_KEY_MERGEOPTIONS.equals(name)) {
					return "--" + name().replace('_'
			} else if (ConfigConstants.CONFIG_KEY_MERGE.equals(section)
					&& subsection == null
					&& ConfigConstants.CONFIG_KEY_FF.equals(name)) {
					switch (this) {
					case NO_FF:
					case FF_ONLY:
					default:
					}
				}
			if (subsection != null)
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().enumValueNotSupported3
						subsection
			else
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().enumValueNotSupported2
						name()));
		}

		public static FastForwardMode valueOf(String section
				String subsection
			if (ConfigConstants.CONFIG_BRANCH_SECTION.equals(section)
					&& subsection != null
					&& ConfigConstants.CONFIG_KEY_MERGEOPTIONS.equals(name)) {
				return valueOf(value.substring(2).replace('-'
						.toUpperCase());
			} else if (ConfigConstants.CONFIG_KEY_MERGE.equals(section)
					&& subsection == null
					&& ConfigConstants.CONFIG_KEY_FF.equals(name)) {
					return FastForwardMode.FF;
					return FastForwardMode.NO_FF;
					return FastForwardMode.FF_ONLY;
			}
			if (subsection != null)
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().enumValueNotSupported3
						subsection
			else
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().enumValueNotSupported2
						value));
		}
