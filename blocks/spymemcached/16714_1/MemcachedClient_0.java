              if (StoreType.add == storeType) {
                Stats.incrTotalAdd(val);
              } else if (StoreType.set == storeType) {
                Stats.incrTotalSet(val);
              } else if (StoreType.replace == storeType) {
                Stats.incrTotalReplace(val);
              }
