		if (this.headers != null && !this.headers.isEmpty()) {
			for (Iterator iterator = this.headers.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				String value = this.headers.get(key);
				conn.setRequestProperty(key
			}
		}
