			case Constants.OBJ_OFS_DELTA: {
				c = ib[p++] & 0xff;
				long ofs = c & 127;
				while ((c & 128) != 0) {
					ofs += 1;
					c = ib[p++] & 0xff;
					ofs <<= 7;
					ofs += (c & 127);
