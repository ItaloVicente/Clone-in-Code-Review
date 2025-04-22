	UploadPack createUploadPack(final Repository dst) {
		return new UploadPack(dst);
	}

	ReceivePack createReceivePack(final Repository dst) {
		return new ReceivePack(dst);
	}

