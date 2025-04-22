			try {
				ref = readRef(prefix + needle, packed);
				if (ref != null) {
					ref = resolve(ref, 0, null, null, packed);
				}
				if (ref != null) {
					break;
				}
			} catch (IOException e) {
						.getCause() instanceof InvalidObjectIdException)) {
					throw e;
				}
