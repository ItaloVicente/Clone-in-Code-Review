				if (rule.getResult()) {
					if (negatePrevious)
						negatePrevious = false;
					else
						return MatchResult.IGNORED;
				} else {
					if (negatePrevious)
						return MatchResult.NOT_IGNORED;
					else
						negatePrevious = true;
				}
