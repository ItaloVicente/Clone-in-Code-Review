		Map<String, Object> got = client.getBulk(keys);
		for(String k : keys) {
			if(got.containsKey(k)) {
				assert Arrays.equals(value, (byte[])got.get(k))
					: "Incorrect result at " + k;
			} else {
				nullKey++;
			}
		}
		System.out.println("Fetched " + got.size() + "/" + keys.size()
				+ " (" + nullKey + " were null)");
	}
