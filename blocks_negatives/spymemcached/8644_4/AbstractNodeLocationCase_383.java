		for(int i=0; i<nodeMocks.length; i++) {
			nodeMocks[i]=mock(MemcachedNode.class, "node#" + i);
			nodes[i]=(MemcachedNode)nodeMocks[i].proxy();
		}
	}
}
