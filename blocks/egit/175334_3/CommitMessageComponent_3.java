			GpgConfig gpgConfig;
			if (repository != null) {
				gpgConfig = new GpgConfig(repository.getConfig());
			} else {
				gpgConfig = new GpgConfig(null, GpgFormat.OPENPGP, null);
			}
