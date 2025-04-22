        case ROUND_ROBIN:
          if (getFailureMode() != FailureMode.Cancel
              && getFailureMode() != FailureMode.Retry)
          {
            throw new IllegalStateException(
              "The round-robin locator is only supported for the 'cancel' "
              + "and 'retry' failure modes.");
          }
          return new RoundRobinLocator(nodes);
