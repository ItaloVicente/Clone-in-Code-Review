			return createArchiveOutputStream(s
					Collections.<String
		}

		public MockOutputStream createArchiveOutputStream(OutputStream s
				Map<String
			for (Map.Entry<String
				try {
					String methodName = "set"
							+ StringUtils.capitalize(p.getKey());
					new Statement(s
							.execute();
				} catch (Exception e) {
					throw new IOException("cannot set option: " + p.getKey()
				}
			}
