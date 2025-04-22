            new HashMap<LoaderType, Refresher>() {
                {
                    put(LoaderType.Carrier, new CarrierRefresher(environment, cluster));
                    put(LoaderType.HTTP, new HttpRefresher(cluster));
                }
            }
