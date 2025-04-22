			int tbarHeight = 0;
			if (size.y > 0)
				tbarHeight = size.y;
			if (tcsize.y > 0)
				tbarHeight = Math.max(tbarHeight, tcsize.y);
			y += tbarHeight;
