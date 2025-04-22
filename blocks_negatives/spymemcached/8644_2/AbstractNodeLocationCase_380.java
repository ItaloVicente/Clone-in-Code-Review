	private void runSequenceAssertion(NodeLocator l, String k, int... seq) {
		int pos=0;
		for(Iterator<MemcachedNode> i=l.getSequence(k); i.hasNext(); ) {
			String loc = i.next().toString();
			try {
				assertEquals("At position " + pos, nodes[seq[pos]].toString(),
					loc);
				i.remove();
				fail("Allowed a removal from a sequence.");
			} catch(UnsupportedOperationException e) {
			}
			catch (ArrayIndexOutOfBoundsException ex) {
			    throw new RuntimeException("Tried to access nodes[" + seq + "[" + pos + "]] erroneously.", ex);
			}
			pos++;
		}
		assertEquals("Incorrect sequence size for " + k, seq.length, pos);
	}
