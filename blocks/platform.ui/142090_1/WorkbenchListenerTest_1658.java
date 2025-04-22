				proceed[0] = false;
				assertEquals(false, workbench.close());
				proceed[0] = true;
				assertEquals(true, workbench.close());
			}
		};
