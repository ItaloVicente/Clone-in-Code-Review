					cellFormulas[i2][j] = new WritableValue<>();
					cellValues[i2][j] = new ComputedCellValue(cellFormulas[i2][j]);
					if (!FUNKY_FORMULAS || i2 == 0 || j == 0) {
						cellFormulas[i2][j].setValue("");
					} else {
						cellFormulas[i2][j].setValue("=" + cellReference(i2 - 1, j) + "+" + cellReference(i2, j - 1));
					}
