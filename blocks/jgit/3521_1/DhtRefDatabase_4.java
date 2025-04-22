				d.getPeeledBuilder().setObjectName(pId.name());

				ChunkKey pKey = ctx.findChunk(pId);
				if (pKey != null)
					d.getPeeledBuilder().setChunkKey(pKey.asString());
				else
					d.getPeeledBuilder().clearChunkKey();
