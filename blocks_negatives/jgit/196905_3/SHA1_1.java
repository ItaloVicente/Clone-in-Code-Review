 * <p>
 * Unlike MessageDigest, this implementation includes the algorithm used by
 * {@code sha1dc} to detect cryptanalytic collision attacks against SHA-1, such
 * sha1collisiondetection</a> for more information.
 * <p>
 * When detectCollision is true (default), this implementation throws
 * {@link org.eclipse.jgit.util.sha1.Sha1CollisionException} from any digest
 * method if a potential collision was detected.
 *
 * @since 4.7
