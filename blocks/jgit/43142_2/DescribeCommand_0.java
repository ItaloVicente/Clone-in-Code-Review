			if (lucky != null) {
				if (!longDesc) {
					return lucky.getName().substring(R_TAGS.length());
				} else {
					return String.format(
							"%s-0-g%s"
							lucky.getName().substring(R_TAGS.length())
							w.getObjectReader()
									.abbreviate(lucky.getPeeledObjectId())
									.name());
				}
			}
