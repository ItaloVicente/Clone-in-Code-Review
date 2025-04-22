			if (!file.delete()) {
				Map<String
						1);
				m.put(file.getAbsolutePath()
						MergeFailureReason.COULD_NOT_DELETE);
				throw new CheckoutConflictException(m);
			}
