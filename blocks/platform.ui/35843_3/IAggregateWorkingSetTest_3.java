			} else {
	            for (int i = 0; i < componenets1.length; i++) {
					if (!componenets1[i].equals(componenets2[i])) {
						fail(nameB + " has lost data in the process of save/restore");
					}
				}
