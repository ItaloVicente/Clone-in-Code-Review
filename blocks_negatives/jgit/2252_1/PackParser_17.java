			crc.reset();
			packOut.seek(end);
			writeWhole(def, typeCode, visit.data);
			oe = new PackedObjectInfo(end, (int) crc.getValue(), baseId);
			entries[entryCount++] = oe;
			end = packOut.getFilePointer();
