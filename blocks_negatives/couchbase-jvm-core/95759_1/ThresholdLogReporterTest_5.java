        void waitUntilZombies(int amount) {
            while (true) {
                if (zombies.size() >= amount) {
                    return;
                }
            }
        }


