		@Override
		protected void end() throws IOException {
			pckOut.end();
		}
	}

	private RevWalk walk;

	private RevFlag ADVERTISED;
