				return Arrays.stream(bindingService.getActiveBindingsFor(commandId)) //
						.filter(binding -> {
							Trigger[] triggers = binding.getTriggers();
							Trigger lastTrigger = triggers[triggers.length - 1];
							return lastTrigger.equals(keyStroke);
						}).findFirst() //
						.map(TriggerSequence::format) //
						.orElse(null);
