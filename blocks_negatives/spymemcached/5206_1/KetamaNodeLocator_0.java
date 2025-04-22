		ketamaNodes=new TreeMap<Long, MemcachedNode>();
        config= conf;

        int numReps= config.getNodeRepetitions();
		for(MemcachedNode node : nodes) {
			if(alg == HashAlgorithm.KETAMA_HASH) {
				for(int i=0; i<numReps / 4; i++) {
					byte[] digest=HashAlgorithm.computeMd5(config.getKeyForNode(node, i));
					for(int h=0;h<4;h++) {
						Long k = ((long)(digest[3+h*4]&0xFF) << 24)
							| ((long)(digest[2+h*4]&0xFF) << 16)
							| ((long)(digest[1+h*4]&0xFF) << 8)
							| (digest[h*4]&0xFF);
						ketamaNodes.put(k, node);
					}
