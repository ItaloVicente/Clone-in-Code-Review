                            EjectionMethod ejectionMethod = EjectionMethod.VALUE;
                            String rawEjectionMethod = bucket.getString("evictionPolicy");
                            if (rawEjectionMethod != null && !rawEjectionMethod.isEmpty()) {
                                if ("fullEviction".equalsIgnoreCase(rawEjectionMethod)) {
                                    ejectionMethod = EjectionMethod.FULL;
                                }
                            }

