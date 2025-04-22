							Writer output = new BufferedWriter(new FileWriter(
									fileName));
							try {
								output.write(sb.toString());
							} finally {
								output.close();
							}
