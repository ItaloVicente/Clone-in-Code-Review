		Callable<ObjectId> callable = new Callable<ObjectId>() {
			@Override
			public ObjectId call() throws Exception {
				return dir.newInserter().insert(Constants.OBJ_BLOB, new byte[0]);
			}
		};
