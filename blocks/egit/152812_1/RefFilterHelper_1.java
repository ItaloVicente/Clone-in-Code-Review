
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("RefFilter ["); //$NON-NLS-1$
			if (filterPattern != null) {
				builder.append("pattern="); //$NON-NLS-1$
				builder.append(filterPattern);
				builder.append(", "); //$NON-NLS-1$
				if (expandedFilterPattern != null
						&& !expandedFilterPattern.equals(filterPattern)) {
					builder.append("expandedPattern="); //$NON-NLS-1$
					builder.append(expandedFilterPattern);
					builder.append(", "); //$NON-NLS-1$
				}
			}
			builder.append("preconfigured="); //$NON-NLS-1$
			builder.append(preconfigured);
			builder.append(", selected="); //$NON-NLS-1$
			builder.append(selected);
			builder.append("]"); //$NON-NLS-1$
			return builder.toString();
		}
