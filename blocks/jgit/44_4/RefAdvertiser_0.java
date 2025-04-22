		PacketLineOutRefAdvertiser(PacketLineOut out) {
			pckOut = out;
		}

		@Override
		protected void writeOne(final CharSequence line) throws IOException {
			pckOut.writeString(line.toString());
		}
