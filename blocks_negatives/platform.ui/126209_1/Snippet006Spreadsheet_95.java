						cellFormulas[i][j] = new WritableValue();
						cellValues[i][j] = new ComputedCellValue(
								cellFormulas[i][j]);
						if (!FUNKY_FORMULAS || i == 0 || j == 0) {
							cellFormulas[i][j].setValue("");
						} else {
							cellFormulas[i][j].setValue("="
									+ cellReference(i - 1, j) + "+"
									+ cellReference(i, j - 1));
						}
