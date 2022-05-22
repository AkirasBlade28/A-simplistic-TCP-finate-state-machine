
public class TCP {
   
	public static String traverseStates(String[] events) {
		String state = "CLOSED" ;// initial state, always //(starting state)
        // Your code here!
        var currentState = InitialStates.valueOf(state);
        for(var e: events) {
        	currentState = currentState.transition(e);
        	if(currentState.equals(InitialStates.ERROR)) {
        		return InitialStates.ERROR.toString();
        	}
        }
        return currentState.toString();       
    }
	public static enum InitialStates {
		CLOSED{

			@Override
			public InitialStates transition(String event) {
				// valid arguments 
				final String a = "APP_PASSIVE_OPEN";
				final String b = "APP_ACTIVE_OPEN";
				
				switch(event) {
					case a:
						return LISTEN;
					case b:
						return SYN_SENT;
					default:
						return ERROR;
				}
			}
			
		},
		
		LISTEN{

			@Override
			public InitialStates transition(String event) {
				final String a = "RCV_SYN";
				final String b = "APP_SEND";
				final String c = "APP_CLOSE";
				
				switch(event){
					case a:
						return SYN_RCVD;
					case b:
						return SYN_SENT;
					case c:
						return CLOSED;
					default:
						return ERROR;
				}
			}
			
		},
		
		SYN_RCVD{

			@Override
			public InitialStates transition(String event) {
				final String a = "APP_CLOSE";
				final String b = "RCV_ACK";
				
				switch(event) {
					case a:
						return FIN_WAIT_1;
					case b:
						return ESTABLISHED;
					default:
						return ERROR;
				}
			}
			
		},
		
		SYN_SENT{

			@Override
			public InitialStates transition(String event) {
				final String a = "RCV_SYN";
				final String b = "RCV_SYN_ACK";
				final String c = "APP_CLOSE";
				
				switch(event) {
					case a:
						return SYN_RCVD;
					case b:
						return ESTABLISHED;
					case c:
						return CLOSED;
					default:
						return ERROR;
				}
			}
			
		},
		
		ESTABLISHED{

			@Override
			public InitialStates transition(String event) {
				final String a = "APP_CLOSE";
				final String b = "RCV_FIN";
				
				switch(event) {
					case a:
						return FIN_WAIT_1;
					case b:
						return CLOSE_WAIT;
					default:
						return ERROR;
				}
			}
			
		},
		
		FIN_WAIT_1{

			@Override
			public InitialStates transition(String event) {
				final String a = "RCV_FIN";
				final String b = "RCV_FIN_ACK";
				final String c = "RCV_ACK";
				
				switch(event) {
					case a:
						return CLOSING;
					case b:
						return TIME_WAIT;
					case c:
						return FIN_WAIT_2;
					default:
						return ERROR;
				}
			}
			
		},
		
		CLOSING{

			@Override
			public InitialStates transition(String event) {
				final String a = "RCV_ACK";
				
				switch(event) {
					case a:
						return TIME_WAIT;
					default: 
						return ERROR;
				}
			}
			
		},
		
		FIN_WAIT_2{

			@Override
			public InitialStates transition(String event) {
				final String a = "RCV_FIN";
				
				switch(event) {
					case a:
						return TIME_WAIT;
					default:
						return ERROR;
				}
			}
			
		},
		
		TIME_WAIT{

			@Override
			public InitialStates transition(String event) {
				final String a = "APP_TIMEOUT";
				
				switch(event) {
					case a:
						return CLOSED;
					default:
						return ERROR;
				}
			}
			
		},
		
		CLOSE_WAIT{

			@Override
			public InitialStates transition(String event) {
				final String a = "APP_CLOSE";
				
				switch(event) {
					case a:
						return LAST_ACK;
					default:
						return ERROR;
				}
			}
		
		},
		
		LAST_ACK{

			@Override
			public InitialStates transition(String event) {
				final String a = "RCV_ACK";
				
				switch(event){
					case a:
						return CLOSED;
					default:
						return ERROR;
				}
			}
			
		},
		
		ERROR{

			@Override
			public InitialStates transition(String event) {
				return ERROR; 
				// not using 
			}
			
		};
		
		public abstract InitialStates transition(String event);
	};
}
