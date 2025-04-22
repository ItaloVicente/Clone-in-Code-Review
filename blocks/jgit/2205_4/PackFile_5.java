

			if (data == null)
				return delta.large(this

			do {
				if (cached)
					cached = false;
				else if (delta.next == null)
					DeltaBaseCache.store(this

				pos = delta.deltaPos;

				final byte[] cmds = decompress(pos + delta.hdrLen
						delta.deltaSize
				if (cmds == null) {
					data = null;
					return delta.large(this
				}

				final long sz = BinaryDelta.getResultSize(cmds);
				if (Integer.MAX_VALUE <= sz)
					return delta.large(this

				final byte[] result;
				try {
					result = new byte[(int) sz];
				} catch (OutOfMemoryError tooBig) {
					data = null;
					return delta.large(this
				}

				BinaryDelta.apply(data
				data = result;
				delta = delta.next;
			} while (delta != null);

			return new ObjectLoader.SmallObject(type

