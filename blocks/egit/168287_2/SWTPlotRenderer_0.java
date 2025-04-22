			float hsb[] = tableBG.getRGB().getHSB();
			if (hsb[2] < 0.5) {
				if (hsb[2] < 0.1) {
					hsb[2] = 0.3f;
				} else {
					hsb[2] *= 1.7;
				}
				labelInner = new RGB(hsb[0], hsb[1], hsb[2]);
				labelTextColor = hsb[2] < 0.5 ? sys_white : sys_black;
				adjustColors = false;
			} else {
				labelInner = INNER_OTHER;
				labelTextColor = sys_black;
			}
