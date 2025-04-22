			try {
				ref = readRef(prefix + needle
				if (ref != null) {
					ref = resolve(ref
					break;
				}
			} catch (IOException e) {
				if (!(!needle.contains("/") && "".equals(prefix) && e
						.getCause() instanceof InvalidObjectIdException)) {
					throw e;
				}
