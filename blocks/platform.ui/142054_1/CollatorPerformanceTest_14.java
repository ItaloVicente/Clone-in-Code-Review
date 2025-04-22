							if (count < 0)
								return;
							if (k % 2 == 0)
								fArray[count] = new String(
										new char[] { (char) (i + 'a'), (char) (j + 'A'), (char) (k + 'a') });
							else if (k % 3 == 0)
								fArray[count] = new String(
										new char[] { (char) (i + 'a'), (char) (j + 'a'), (char) (k + 'A') });
