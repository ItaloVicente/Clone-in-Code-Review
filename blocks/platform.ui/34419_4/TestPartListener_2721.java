package org.eclipse.ui.internal.misc;

import java.util.Vector;

public class StringMatcher {
    protected String fPattern;

    protected int fLength; // pattern length

    protected boolean fIgnoreWildCards;

    protected boolean fIgnoreCase;

    protected boolean fHasLeadingStar;

    protected boolean fHasTrailingStar;

    protected String fSegments[]; //the given pattern is split into * separated segments

    protected int fBound = 0;

    protected static final char fSingleWildCard = '\u0000';

    public static class Position {
        int start; //inclusive

        int end; //exclusive

        public Position(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    }

    public StringMatcher(String pattern, boolean ignoreCase,
            boolean ignoreWildCards) {
        if (pattern == null) {
			throw new IllegalArgumentException();
		}
        fIgnoreCase = ignoreCase;
        fIgnoreWildCards = ignoreWildCards;
        fPattern = pattern;
        fLength = pattern.length();

        if (fIgnoreWildCards) {
            parseNoWildCards();
        } else {
            parseWildCards();
        }
    }

    public StringMatcher.Position find(String text, int start, int end) {
        if (text == null) {
			throw new IllegalArgumentException();
		}

        int tlen = text.length();
        if (start < 0) {
			start = 0;
		}
        if (end > tlen) {
			end = tlen;
		}
        if (end < 0 || start >= end) {
			return null;
		}
        if (fLength == 0) {
			return new Position(start, start);
		}
        if (fIgnoreWildCards) {
            int x = posIn(text, start, end);
            if (x < 0) {
				return null;
			}
            return new Position(x, x + fLength);
        }

        int segCount = fSegments.length;
        if (segCount == 0) {
			return new Position(start, end);
		}

        int curPos = start;
        int matchStart = -1;
        int i;
        for (i = 0; i < segCount && curPos < end; ++i) {
            String current = fSegments[i];
            int nextMatch = regExpPosIn(text, curPos, end, current);
            if (nextMatch < 0) {
				return null;
			}
            if (i == 0) {
				matchStart = nextMatch;
			}
            curPos = nextMatch + current.length();
        }
        if (i < segCount) {
			return null;
		}
        return new Position(matchStart, curPos);
    }

    public boolean match(String text) {
    	if(text == null) {
			return false;
		}
        return match(text, 0, text.length());
    }

    public boolean match(String text, int start, int end) {
        if (null == text) {
			throw new IllegalArgumentException();
		}

        if (start > end) {
			return false;
		}

        if (fIgnoreWildCards) {
			return (end - start == fLength)
                    && fPattern.regionMatches(fIgnoreCase, 0, text, start,
                            fLength);
		}
        int segCount = fSegments.length;
        if (segCount == 0 && (fHasLeadingStar || fHasTrailingStar)) {
			return true;
		}
        if (start == end) {
			return fLength == 0;
		}
        if (fLength == 0) {
			return start == end;
		}

        int tlen = text.length();
        if (start < 0) {
			start = 0;
		}
        if (end > tlen) {
			end = tlen;
		}

        int tCurPos = start;
        int bound = end - fBound;
        if (bound < 0) {
			return false;
		}
        int i = 0;
        String current = fSegments[i];
        int segLength = current.length();

        if (!fHasLeadingStar) {
            if (!regExpRegionMatches(text, start, current, 0, segLength)) {
                return false;
            } else {
                ++i;
                tCurPos = tCurPos + segLength;
            }
        }
        if ((fSegments.length == 1) && (!fHasLeadingStar)
                && (!fHasTrailingStar)) {
            return tCurPos == end;
        }
        while (i < segCount) {
            current = fSegments[i];
            int currentMatch;
            int k = current.indexOf(fSingleWildCard);
            if (k < 0) {
                currentMatch = textPosIn(text, tCurPos, end, current);
                if (currentMatch < 0) {
					return false;
				}
            } else {
                currentMatch = regExpPosIn(text, tCurPos, end, current);
                if (currentMatch < 0) {
					return false;
				}
            }
            tCurPos = currentMatch + current.length();
            i++;
        }

        if (!fHasTrailingStar && tCurPos != end) {
            int clen = current.length();
            return regExpRegionMatches(text, end - clen, current, 0, clen);
        }
        return i == segCount;
    }

    private void parseNoWildCards() {
        fSegments = new String[1];
        fSegments[0] = fPattern;
        fBound = fLength;
    }

    private void parseWildCards() {
        if (fPattern.startsWith("*")) { //$NON-NLS-1$
			fHasLeadingStar = true;
		}
        if (fPattern.endsWith("*")) {//$NON-NLS-1$
            if (fLength > 1 && fPattern.charAt(fLength - 2) != '\\') {
                fHasTrailingStar = true;
            }
        }

        Vector temp = new Vector();

        int pos = 0;
        StringBuffer buf = new StringBuffer();
        while (pos < fLength) {
            char c = fPattern.charAt(pos++);
            switch (c) {
            case '\\':
                if (pos >= fLength) {
                    buf.append(c);
                } else {
                    char next = fPattern.charAt(pos++);
                    if (next == '*' || next == '?' || next == '\\') {
                        buf.append(next);
                    } else {
                        buf.append(c);
                        buf.append(next);
                    }
                }
                break;
            case '*':
                if (buf.length() > 0) {
                    temp.addElement(buf.toString());
                    fBound += buf.length();
                    buf.setLength(0);
                }
                break;
            case '?':
                buf.append(fSingleWildCard);
                break;
            default:
                buf.append(c);
            }
        }

        if (buf.length() > 0) {
            temp.addElement(buf.toString());
            fBound += buf.length();
        }

        fSegments = new String[temp.size()];
        temp.copyInto(fSegments);
    }

    protected int posIn(String text, int start, int end) {//no wild card in pattern
        int max = end - fLength;

        if (!fIgnoreCase) {
            int i = text.indexOf(fPattern, start);
            if (i == -1 || i > max) {
				return -1;
			}
            return i;
        }

        for (int i = start; i <= max; ++i) {
            if (text.regionMatches(true, i, fPattern, 0, fLength)) {
				return i;
			}
        }

        return -1;
    }

    protected int regExpPosIn(String text, int start, int end, String p) {
        int plen = p.length();

        int max = end - plen;
        for (int i = start; i <= max; ++i) {
            if (regExpRegionMatches(text, i, p, 0, plen)) {
				return i;
			}
        }
        return -1;
    }

    protected boolean regExpRegionMatches(String text, int tStart, String p,
            int pStart, int plen) {
        while (plen-- > 0) {
            char tchar = text.charAt(tStart++);
            char pchar = p.charAt(pStart++);

            if (!fIgnoreWildCards) {
                if (pchar == fSingleWildCard) {
                    continue;
                }
            }
            if (pchar == tchar) {
				continue;
			}
            if (fIgnoreCase) {
                if (Character.toUpperCase(tchar) == Character
                        .toUpperCase(pchar)) {
					continue;
				}
                if (Character.toLowerCase(tchar) == Character
                        .toLowerCase(pchar)) {
					continue;
				}
            }
            return false;
        }
        return true;
    }

    protected int textPosIn(String text, int start, int end, String p) {

        int plen = p.length();
        int max = end - plen;

        if (!fIgnoreCase) {
            int i = text.indexOf(p, start);
            if (i == -1 || i > max) {
				return -1;
			}
            return i;
        }

        for (int i = start; i <= max; ++i) {
            if (text.regionMatches(true, i, p, 0, plen)) {
				return i;
			}
        }

        return -1;
    }
}
