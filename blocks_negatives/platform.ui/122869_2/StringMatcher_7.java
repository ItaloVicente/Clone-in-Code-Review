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
