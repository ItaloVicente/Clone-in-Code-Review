	class SelectRepresentationTask implements Callable {
		private PackWriter packer;
		private ObjectToPack otp;
		private WindowCursor curs;

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

