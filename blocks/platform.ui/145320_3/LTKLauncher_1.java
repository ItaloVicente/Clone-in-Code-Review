			Iterator<Entry<String, Object>> it = commandParameters.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> pair = it.next();
				c.addVariable(pair.getKey(), pair.getValue());
				it.remove();
			}
