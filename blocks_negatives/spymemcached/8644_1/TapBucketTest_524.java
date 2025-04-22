		while(tc.hasMoreMessages()) {
			ResponseMessage m;
			if ((m = tc.getNextMessage()) != null) {
				String key = m.getKey() + ", + new String(m.getValue());
-				if (items.containsKey(key)) {
-					items.put(key, new Boolean(true));
-				} else {
-					System.err.println(Error - Item: " + key + " was found" +
							", but shoult not have been found");
					failed = true;
				}
			}
		}
