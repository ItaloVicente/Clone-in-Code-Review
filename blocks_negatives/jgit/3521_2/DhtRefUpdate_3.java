				key = ctx.findChunk(pId);
				pId = key != null ? new RefDataUtil.IdWithChunk(pId, key) : pId;
				return RefDataUtil.peeled(newId, pId);
			} else if (obj != null)
				return RefDataUtil.peeled(newId, null);
			else
				return RefDataUtil.id(newId);
