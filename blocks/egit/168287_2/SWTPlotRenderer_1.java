		if (adjustColors) {
			float hsb[] = tableBG.getRGB().getHSB();
			if (hsb[2] < 0.5) {
				hsb = labelInner.getHSB();
				if (hsb[2] >= 0.5) {
					if (!(labelInner.red == labelInner.blue
							&& labelInner.blue == labelInner.green)) {
						hsb[1] = (float) Math.min(1.0, hsb[1] * 1.5);
					}
					hsb[2] *= 0.7;
					labelInner = new RGB(hsb[0], hsb[1], hsb[2]);
				}
				labelTextColor = hsb[2] < 0.5 ? sys_white : sys_black;
			}
		}
