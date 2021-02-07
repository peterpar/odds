package oddstable;

import java.io.PrintStream;
import java.util.function.IntBinaryOperator;

import oddstable.Odds.RoundingType;

public class ADCrossTab {
	


	class FigureOddsForDefender implements IntBinaryToStringOperator {

		@Override
		public String applyAsInt(int left, int right) {
			Odds o = new Odds(left, right);
			Odds found = pol.searchWithDefenderFavorization(o);
			return found.toString();
		}
	}

	class FigureOddsTrueRounding implements IntBinaryToStringOperator {

		@Override
		public String applyAsInt(int left, int right) {
			Odds o = new Odds(left, right);
			Odds found = pol.searchWithTrueRounding(o);
			return found.toString();
		}
	}
	
	class FigureOddsSCSTrueRounding implements IntBinaryToStringOperator {

		@Override
		public String applyAsInt(int left, int right) {
			Odds o = new Odds(left, right);
			Odds found = pol.searchWithSCSTrueRounding(o);
			return found.toString();
		}
	}
	
	
	
	PrintStream myout = null;
	int defender_cnts;
	int attacker_cnts;
	PossibleOddsList pol = null;
	RoundingType rt;
	IntBinaryToStringOperator oddsCalculator = null;

	public ADCrossTab(PossibleOddsList pol, int d_cnt, int a_cnt, RoundingType a_rt) {
		myout = System.out;
		defender_cnts = d_cnt;
		attacker_cnts = a_cnt;
		this.pol = pol;
		this.rt = a_rt;

		if( rt == Odds.RoundingType.ForDefender )
			oddsCalculator = new FigureOddsForDefender() ;
		else
		if( rt == Odds.RoundingType.TrueRounding)
			oddsCalculator = new FigureOddsTrueRounding() ;
		else
		if( rt == Odds.RoundingType.SCSRounding)
			oddsCalculator = new FigureOddsSCSTrueRounding();
		

	}

	void outLine(int attackf, IntBinaryToStringOperator bo ) {
		myout.print(attackf);
		
		for (int df = 1; df <= defender_cnts; df++) {
			myout.print(",");
			myout.print( bo.applyAsInt(attackf, df) );
		}
		myout.print("\n");
	}

	void printHeader() {
		myout.print("Attacker Down");
		for (int df = 1; df <= defender_cnts; df++) {
			myout.print("," + df);
		}
		myout.print("\n");
	}

	public void toTable( PrintStream ps ) {
		PrintStream prev = myout ;
		myout = ps ;
		printHeader();

		for( int af = 1 ; af <= attacker_cnts ; af++ ) {
			outLine( af, oddsCalculator);
		}
		myout = prev;
	}
	
	public void toTable() {
		toTable( System.out );
	}

	
	public String convertOne( int af, int df ) {
		return oddsCalculator.applyAsInt(af, df);
	}
	
}
