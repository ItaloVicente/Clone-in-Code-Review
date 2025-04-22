				{ 1, 1 }, { 55, 55 }, { 56, 57 }, // +1 bitmap from branch A55
				{ 99, 100 }, // still +1 branch @55
				{ 100, 100 }, // 101 commits, only 100 newest
				{ 116, 100 }, // @55 still in 100 newest bitmaps
				{ 176, 101 }, // @55 branch tip is not in 100 newest
				{ 213, 101 }, // 216 commits, @115&@175 in 100 newest
				{ 214, 102 }, // @55 branch tip, merge @115, @177 in newest
				{ 236, 102 }, // all 4 merge points in history
				{ 273, 102 }, // 277 commits, @175&@235 in newest
				{ 274, 103 }, // @55, @115, merge @175, @235 in newest
				{ 334, 103 }, // @55,@115,@175, @235 in newest
				{ 335, 104 }, // @55,@115,@175, merge @235
				{ 435, 104 }, // @55,@115,@175,@235 tips
				{ 436, 104 }, // force @236
