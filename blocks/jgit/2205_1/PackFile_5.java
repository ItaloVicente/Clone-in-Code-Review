			final byte[] result;
			try {
				result = new byte[(int) sz];
			} catch (OutOfMemoryError tooBig) {
				return large(pack
			}

			BinaryDelta.apply(base

			if (next == null && !(ldr instanceof CachedBase))
				DeltaBaseCache.store(pack
