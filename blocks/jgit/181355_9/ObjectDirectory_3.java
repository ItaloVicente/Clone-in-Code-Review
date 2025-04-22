	class SelectRepresentationTask implements Callable<Void> {
		private final PackWriter packer;
		private final ObjectToPack otp;
		private final WindowCursor curs;

		SelectRepresentationTask(PackWriter packer
			this.packer = packer;
			this.otp = otp;
			this.curs = curs;
		}
		@Override
		public Void call() throws Exception {
			packed.selectRepresentation(packer
			return null;
		}
	}

