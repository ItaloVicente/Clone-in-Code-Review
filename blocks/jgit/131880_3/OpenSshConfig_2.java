			if (config == null) {
				config = new Config() {

					@Override
					public String getHostname() {
						return Host.this.getHostName();
					}

					@Override
					public String getUser() {
						return Host.this.getUser();
					}

					@Override
					public int getPort() {
						return Host.this.getPort();
					}

					@Override
					public String getValue(String key) {
							if (!OpenSshConfigFile.flag(
									Host.this.entry.getValue(mapKey(key)))) {
								return "none
							}
							return "zlib@openssh.com
						}
						return Host.this.entry.getValue(mapKey(key));
					}

					@Override
					public String[] getValues(String key) {
						List<String> values = Host.this.entry
								.getValues(mapKey(key));
						if (values == null) {
							return new String[0];
						}
						return values.toArray(new String[0]);
					}
				};
			}
