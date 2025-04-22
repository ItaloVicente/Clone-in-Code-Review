            try {
                Class<? extends CouchbaseRequest> requestClass = currentRequest.getClass();
                String simpleName = classNameCache.get(requestClass);
                if (simpleName == null) {
                    simpleName = requestClass.getSimpleName();
                    classNameCache.put(requestClass, simpleName);
                }
