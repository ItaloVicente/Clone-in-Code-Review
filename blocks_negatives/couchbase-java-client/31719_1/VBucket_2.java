      case 0: return replica1;
      case 1: return replica2;
      case 2: return replica3;
      default:
        throw new IllegalArgumentException("No more than " + MAX_REPLICAS
          + " replicas allowed.");
