			testMyers(10000);
			testMyers(20000);
			testMyers(10000);
			testMyers(20000);

			List<PerfData> myersPerfData = new LinkedList<PerfData>();
			myersPerfData.add(testMyers(10000));
			myersPerfData.add(testMyers(20000));
			myersPerfData.add(testMyers(40000));
			myersPerfData.add(testMyers(80000));
			myersPerfData.add(testMyers(160000));
			myersPerfData.add(testMyers(320000));
			myersPerfData.add(testMyers(640000));
			myersPerfData.add(testMyers(1280000));
