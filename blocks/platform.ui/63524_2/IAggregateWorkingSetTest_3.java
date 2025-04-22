				for (int i = 0; i < componenents1.length; i++) {
					if (!componenents1[i].equals(componenents2[i])) {
						assertEquals(nameB + " has lost data in the process of save/restore: " + restoredB,
								componenents1[i].toString(), componenents2[i].toString());
						fail("equals() and toString() do not match for: " + componenents1[i] + " and "
								+ componenents2[i]);
