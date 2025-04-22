      if (this.major > major) {
        return true;
      } else if (this.major == major) {
        if (this.minor > minor) {
          return true;
        } else if (this.minor == minor) {
          if (this.bugfix >= bugfix) {
            return true;
          }
        }
      }
      return false;
