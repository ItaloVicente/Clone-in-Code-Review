            } else {
                currentMatch = regExpPosIn(text, tCurPos, end, current);
                if (currentMatch < 0) {
                	found = false;
				}
            }
            if(!found)
            	continue;
            tCurPos = currentMatch + current.length();
            i++;
        }

        /* process final segment */
        if (!fHasTrailingStar && tCurPos != end) {
            int clen = current.length();
            if (regExpRegionMatches(text, end - clen, current, 0, clen))
				return true;
			continue;
        }
        if (i == segCount)
