
		@Override
		public String toString() {
			if (section == null)
				return "<empty>";
			StringBuilder b = new StringBuilder(section);
			if (subsection != null)
				b.append(".").append(subsection);
			if (name != null)
				b.append(".").append(name);
			if (value != null)
				b.append("=").append(value);
			return b.toString();
		}
