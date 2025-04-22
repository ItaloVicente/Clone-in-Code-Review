		
		int j = 0;
		boolean set = false;
		do {
			set = client.set(key, 0, value).get();
			j++;
		} while (!set && j < 10);
		assert set == true;
		
