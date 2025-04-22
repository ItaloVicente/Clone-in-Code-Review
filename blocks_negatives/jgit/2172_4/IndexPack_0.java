			type = typeCode;
			data = inflateAndReturn(Source.FILE, sz);
			break;
		case Constants.OBJ_OFS_DELTA: {
			c = readFrom(Source.FILE) & 0xff;
			while ((c & 128) != 0)
				c = readFrom(Source.FILE) & 0xff;
			data = BinaryDelta.apply(data, inflateAndReturn(Source.FILE, sz));
			break;
		}
		case Constants.OBJ_REF_DELTA: {
			crc.update(buf, fill(Source.FILE, 20), 20);
			use(20);
			data = BinaryDelta.apply(data, inflateAndReturn(Source.FILE, sz));
