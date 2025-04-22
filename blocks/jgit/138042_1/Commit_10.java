			}
			if (noGpgSign) {
				commitCmd.setSign(Boolean.FALSE);
			} else if (gpgSigningKey != null) {
				commitCmd.setSign(Boolean.TRUE);
				if (!gpgSigningKey.equals(GpgSignHandler.DEFAULT)) {
					commitCmd.setSigningKey(gpgSigningKey);
				}
			}
			if (only && paths.isEmpty()) {
