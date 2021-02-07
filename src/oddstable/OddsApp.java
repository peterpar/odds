package oddstable;

import java.io.PrintStream;

public class OddsApp {

	public static void main(String[] args) {
		PrintStream ps = System.out ;

		// Demyansk Shield Odds
		//PossibleOddsList pol = new PossibleOddsList("1-3,1-2,3-4,1-1,3-2,2-1,5-2,3-1,4-1,5-1,6-1,7-1,8-1");

		// CSC Odds
		PossibleOddsList pol = new PossibleOddsList("1-2,1-1,2-1,3-1,4-1");
		
		ps.println(   pol.toString() );
		new ADCrossTab( pol, 20,40, Odds.RoundingType.SCSRounding ).toTable(ps);
		
	}

}
