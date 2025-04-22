 * Objects of classes that implement this interface
 * can be registered for certain object type
 * in the IObjectContributorManager. Unlike with extenders,
 * all the matching contributors will be processed
 * in a sequence.
 * <p>By implementing 'isApplicableTo' method,
 * a contributor can tell the manager to skip it
 * if the object is of the desired type, but its
 * other properties do not match additional
 * requirements imposed by the contributor.
