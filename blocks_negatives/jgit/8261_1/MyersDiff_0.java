 * lines of text B as rows ("y").  Now you try to find the shortest "edit path"
 * from the upper left corner to the lower right corner, where you can
 * always go horizontally or vertically, but diagonally from (x,y) to
 * (x+1,y+1) only if line x in text A is identical to line y in text B.
 *
 * Myers' fundamental concept is the "furthest reaching D-path on diagonal k":
 * a D-path is an edit path starting at the upper left corner and containing
 * exactly D non-diagonal elements ("differences").  The furthest reaching
 * D-path on diagonal k is the one that contains the most (diagonal) elements
 * which ends on diagonal k (where k = y - x).
 *
