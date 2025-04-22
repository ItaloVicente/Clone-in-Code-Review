			if (remaining > 0) {
				for (final RevObject o : commits) {
					if (!o.has(SEEN))
						missing.put(o
				}
				throw new MissingBundlePrerequisiteException(transport.uri
						missing);
