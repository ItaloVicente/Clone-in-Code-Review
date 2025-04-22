			if (getGpgSignature() != null) {
				os.write(hgpgsig);
				os.write(' ');
				writeGpgSignatureString(getGpgSignature().toExternalString()
				os.write('\n');
			}

