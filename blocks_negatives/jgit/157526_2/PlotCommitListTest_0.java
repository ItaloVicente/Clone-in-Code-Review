			assertEquals("b2 is an a different position", bPos,
					pcl.get(7).lane.position);
			assertEquals("b3 is on a different position", bPos,
					pcl.get(4).lane.position);

			assertNotEquals("b lane is blocked by c", bPos,
					pcl.get(8).lane.position);
			assertNotEquals("b lane is blocked by a2", bPos,
					pcl.get(6).lane.position);
			assertNotEquals("b lane is blocked by d", bPos,
					pcl.get(5).lane.position);
