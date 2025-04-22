                }).last().map(new Func1<Service, Boolean>() {
                        @Override
                        public Boolean call(Service service) {
                            return true;
                        }
                    });
