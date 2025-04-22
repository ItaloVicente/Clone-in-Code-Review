 * Index instances from a higher level invocation message some state into a
 * lower level invocation by passing the {@link #nCommon} array from the higher
 * invocation into the two sub-steps as {@link #pCommon}. This permits some
 * matching work that was already done in the higher invocation to be reused in
 * the sub-step and can save a lot of time when element equality is expensive.
 *
