				if (subRepo != null) {
					try {
						cloneSubmodules(subRepo);
					} finally {
						subRepo.close();
					}
				}
