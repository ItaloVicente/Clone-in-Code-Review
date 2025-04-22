			if (delta != null) {
				if (data == null)
					return delta.large(this

				do {
					if (!cached && delta.next == null)
						DeltaBaseCache.store(this

					pos = delta.deltaPos;
					data = delta.apply(data
					if (data == null)
						return delta.large(this

					delta = delta.next;
					cached = false;
				} while (delta != null);
