			case CHUNK_ID_BLOOM_INDEXES:
				lengthReaded = loadChunkDataBasedOnFanout(fd
						bloomIdx);
				bloomIdxLoaded = true;
				break;
			case CHUNK_ID_BLOOM_DATA:
				lengthReaded = loadChunkBloomData(fd
				break;
