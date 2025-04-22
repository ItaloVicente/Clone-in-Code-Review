                        return Observable.from(settings);
                    } catch (Exception e) {
                        throw new TranscodingException("Could not decode cluster info.", e);
                    }
                }
            });
