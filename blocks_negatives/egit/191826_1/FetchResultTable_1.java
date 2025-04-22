					if (f1.getChildren(f1).length > 0
							&& f2.getChildren(f2).length == 0)
						return -1;
					if (f1.getChildren(f1).length == 0
							&& f2.getChildren(f2).length > 0)
						return 1;

