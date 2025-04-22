				if (0 < b.useCount) {
					BaseChunk.Builder c = BaseChunk.newBuilder();
					c.setRelativeStart(b.relativeStart);
					c.setChunkKey(b.key.asString());
					list.add(c.build());
				}
