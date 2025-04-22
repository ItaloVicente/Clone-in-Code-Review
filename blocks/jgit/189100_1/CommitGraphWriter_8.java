
		if (totalBloomFilterDataSize > 0) {
			chunks[numChunks].id = CommitGraphConstants.CHUNK_ID_BLOOM_INDEXES;
			chunks[numChunks].size = 4 * commitDataList.size();
			numChunks++;

			chunks[numChunks].id = CommitGraphConstants.CHUNK_ID_BLOOM_DATA;
			chunks[numChunks].size = 12 + totalBloomFilterDataSize;
			numChunks++;
		}
