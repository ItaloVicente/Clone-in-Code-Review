				if (rule.getResult()) {
					if (negateFirstMatch)
						negateFirstMatch = false;
					else
						return MatchResult.IGNORED;
				} else {
					if (negateFirstMatch)
						return MatchResult.NOT_IGNORED;
					else
						negateFirstMatch = true;
				}
