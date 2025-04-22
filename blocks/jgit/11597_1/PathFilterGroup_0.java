			byte[] newMax = new byte[max.length + 1];
			for (int i = 0; i < max.length; ++i)
				if ((max[i]&0xFF) < '/')
					newMax[i] = '/';
				else
					newMax[i] = max[i];
			newMax[newMax.length - 1] = '/';
			max = newMax;
