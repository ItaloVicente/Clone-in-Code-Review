			long need;
			if (ent.buffer != null)
				need = DeltaIndex.estimateIndexSize(ent.buffer.length) - ent.buffer.length;
			else
				need = estimateSize(ent);
			checkLoadable(ent

