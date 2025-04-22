					long base = c & 127;
					while ((c & 128) != 0) {
						base += 1;
						c = ib[p++] & 0xff;
						base <<= 7;
						base += (c & 127);
					}
					base = pos - base;
					delta = new Delta(delta

					DeltaBaseCache.Entry e = DeltaBaseCache.get(this
					if (e != null) {
						ldr = new CachedBase(e);
						break SEARCH;
					} else {
						pos = base;
						continue SEARCH;
					}
