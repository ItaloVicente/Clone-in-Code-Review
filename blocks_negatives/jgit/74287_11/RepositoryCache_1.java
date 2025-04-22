			}

			private boolean isExpired(Repository db) {
				return db.useCnt.get() == 0 && (System.currentTimeMillis()
						- db.closedAt.get() > 20000);
			}
		};
