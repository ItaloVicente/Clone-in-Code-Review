				for (final Ref r : refs.values()) {
					final RevObject o;
					try {
						o = walk.parseAny(r.getObjectId());
					} catch (IOException e) {
						continue;
