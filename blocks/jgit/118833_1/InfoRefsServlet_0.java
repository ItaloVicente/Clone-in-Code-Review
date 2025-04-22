				Constants.CHARSET)) {
			final RefAdvertiser adv = new RefAdvertiser() {
				@Override
				protected void writeOne(final CharSequence line)
						throws IOException {
					out.append(line.toString().replace(' '
				}
