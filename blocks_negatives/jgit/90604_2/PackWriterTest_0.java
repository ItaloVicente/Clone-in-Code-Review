				ObjectId.fromString("5b6e7c66c276e7610d4a73c70ec1a1f7c1003259"),
				ObjectId.fromString("6ff87c4664981e4397625791c8ea3bbb5f2279a3") };
		if (deltaReuse) {
			ObjectId temp = expectedOrder[4];
			expectedOrder[4] = expectedOrder[5];
			expectedOrder[5] = temp;
