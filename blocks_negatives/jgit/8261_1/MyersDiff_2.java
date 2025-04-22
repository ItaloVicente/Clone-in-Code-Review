 * The overall (runtime) complexity is
 *
 *	O(N * D^2 + 2 * N/2 * (D/2)^2 + 4 * N/4 * (D/4)^2 + ...)
 *	= O(N * D^2 * 5 / 4) = O(N * D^2),
 *
 * (With each step, we have to find the middle parts of twice as many regions
 * as before, but the regions (as well as the D) are halved.)
 *
 * So the overall runtime complexity stays the same with linear space,
 * albeit with a larger constant factor.
