import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	private static final Logger logger = Logger.getLogger(".Main");

	public Main() {
		// TODO Auto-generated constructor stub
	}// end constructor

	public static void main(String[] args) {
		logger.finest("entering main");
		if (logger.isLoggable(Level.FINEST)) {
			// TODO: NO JAVADOC COMMENT AVAILABLE
			logger.finest("entry parameters: args=" + Arrays.toString(args));
		}// end if
		logger.finer("Instantiating variable with identifier: i");
		// TODO Auto-generated method stub
		int i = 0;
		while (i < 5) {
			logger.config("Inside while statement with expression: i < 5");
			for (int j = 0; j < 5; j++) {
				logger.info("Inside for statement with expression: j < 5");
				if (j == 1) {
					logger.finer("Inside if condition with expression: j == 1");
					logger.finer("Method call to System.out.println(j)");
					System.out.println(j);
				} else {
					logger.finer("Inside else condition.");
					switch (j) {
					case 2:
						logger.config("Inside switch/case statement with expression: 2");
						logger.config("Method call to System.out.println(j);\n");
						System.out.println(j);
						logger.config("Breaking the control structure.");
						break;
					default:
						logger.config("Inside switch/case statement with expression: default");
						logger.config("Method call to System.out.println(i);\n");
						System.out.println(i);
					}// end switch
				}// end if/else
			}// end for
			i++;
		}// end while
		logger.finest("exiting main");
	}// end main
}// end class
