							List<RefSpec> specs = new ArrayList<RefSpec>(1);
							specs.add(spec);
							int timeout = Activator
									.getDefault()
									.getPreferenceStore()
									.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
							FetchResult fetchRes;
