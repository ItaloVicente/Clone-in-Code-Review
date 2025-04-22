        return annotation.value();
    }

    private static boolean hasAnnotation(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
        return findAnnotation(element, annotationClass) != null;
    }

    private static <T extends Annotation> T findAnnotation(AnnotatedElement element, Class<T> annotationClass) {
        for (Annotation a : element.getAnnotations()) {
            T meta = findAnnotationRecursive(a, annotationClass, new HashSet<Class>());
            if (meta != null) {
                return meta;
            }
        }
        return null;
    }

    private static <T extends Annotation> T findAnnotationRecursive(Annotation annotation, Class<T> annotationClass, Set<Class> seen) {
        final Class c = annotation.annotationType();

        if (!seen.add(c)) {
            return null;
        }

        if (c.equals(annotationClass)) {
            return annotationClass.cast(annotation);
        }

        for (Annotation meta : c.getAnnotations()) {
            T found = findAnnotationRecursive(meta, annotationClass, seen);
            if (found != null) {
                return found;
            }
        }
        return null;
