
		@Override
		public String toString() {
			if (section == null)
				return "<emtpy>";
			StringBuilder b = new StringBuilder("[").append(section);
			if (subsection != null)
				b.append(".").append(subsection);
			if (name != null)
				b.append(".").append(name);
			b.append("]");
			if (value != null)
				b.append("=").append(value);
			return b.toString();
		}
