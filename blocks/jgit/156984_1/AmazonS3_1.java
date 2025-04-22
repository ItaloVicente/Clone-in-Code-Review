			} else if ("LastModified".equals(name)) {
			        keyLastModified = Instant.parse(data.toString());
		        } else if ("Contents".equals(name)) {
			        entries.add(new KeyInfo(keyName
			}

