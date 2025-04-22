			case CHUNK_ID_BLOOM_INDEXES:
				writeBloomIndexes(out);
				break;
			case CHUNK_ID_BLOOM_DATA:
				writeBloomData(out);
				break;
