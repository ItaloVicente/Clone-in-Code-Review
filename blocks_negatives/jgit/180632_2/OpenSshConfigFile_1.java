				current.clear();
				for (String name : parseList(argValue)) {
					if (name == null || name.isEmpty()) {
						continue;
					}
					HostEntry c = entries.get(name);
					if (c == null) {
						c = new HostEntry();
						entries.put(name, c);
					}
					current.add(c);
				}
				continue;
			}

			if (current.isEmpty()) {
