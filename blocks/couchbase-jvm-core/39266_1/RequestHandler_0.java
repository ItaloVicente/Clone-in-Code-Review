                try {
                    configuration.set(config);
                    reconfigure(config).subscribe();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }

