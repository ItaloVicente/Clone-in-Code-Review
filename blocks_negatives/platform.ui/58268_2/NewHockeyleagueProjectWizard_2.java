					createInitialModel(resource);

					try {
						resource.save(Collections.EMPTY_MAP);
					} catch (IOException e) {
						e.printStackTrace();
					}

				} finally {
					monitor.done();
