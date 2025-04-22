			try {
				ref = readRef(prefix + needle
				if (ref != null) {
					ref = resolve(ref
					break;
				}
			} catch (IOException e) {
				caughtException = e;
				if (!(e.getCause() instanceof InvalidObjectIdException)) {
					throw e;
				}
