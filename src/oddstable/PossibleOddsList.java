package oddstable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import oddstable.Odds.Special;

public class PossibleOddsList extends ArrayList<Odds> {

	
	/**
	 * Process string like 1-2,1-1,3-2, etc.
	 * @param all_odds_str
	 */
	public PossibleOddsList( String all_odds_str ) {
		
		String [] all_odds_a = all_odds_str.split(",");
		
		for( String o : all_odds_a ) {
			String [] fm_to_str = o.split("-");
			add( new Odds( fm_to_str[0], fm_to_str[1] ));
		}
		Collections.sort(this);
				
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		this.forEach(  o -> {
			sb.append( o.toDebugString() );
			sb.append(", "); });
		return sb.toString();
	}
	/**
	 * Find odds in list of possible odds, rounding for 
	 * defender
	 * @param actual
	 * @return
	 */
	public Odds searchWithDefenderFavorization( Odds actual ) {
		int ix = 0;
		
		
		
		for( Odds item : this ) {
			
			if( item.decimal > actual.decimal && ix==0 )
			{
				return new Odds(Special.BELOW);
			}
			
			
			if( (ix+1) >= this.size() ) {
				if( item.decimal == actual.decimal )
					return item;
				else
					return new Odds(Special.OVER);
			}
						 
			
			if( item.decimal <= actual.decimal && 
					this.get(ix+1).decimal > actual.decimal )
				return item;
			
			ix++;
		}
		return null;
	}

	
	/**
	 * Rounding to closest ratio, over uses highest defined odds column (SCSrules)
	 * Below is game dependent - assume only whole number odds so below, means
	 * closer to lower odds, example lowest table odds are 1-2, so if odds are closer
	 * to 1-3, they are BELOW
	 * simmilarly, odds are ABOVE, if closer to next odds (not in table)
	 * @param actual exact attacker to defender odds in object
	 * @return
	 */
	public Odds searchWithTrueRounding( Odds actual ) {
		int ix = 0;
		Odds lowest = this.get(0);
		Odds pre_lower = new Odds(1,lowest.to+1);
		double below_split = (lowest.decimal+pre_lower.decimal)/2.0;
		Odds highest = this.get(this.size()-1);
		Odds post_highest = new Odds( highest.fm+1, 1);
		double above_split = (highest.decimal+post_highest.decimal)/2.0;
		
		// check for extreme odds first
		if( actual.compareTo(lowest)==-1 )
		{
			if( actual.decimal < below_split )
				return new Odds(Special.BELOW);
			else
				return lowest;
		}
		
		if( actual.compareTo(highest) == 1 ) {
			if( actual.decimal > above_split )
				return new Odds(Special.OVER);
			else
				return highest;
		}

		// since we have just less than 17 odds slots, linear search is better
		ix=0;
		for( Odds item : this ) {
				
			Odds nextItem = this.get(ix+1);
			
			if( item.compareTo(actual) <= 0 && nextItem.compareTo(actual) >= 0 ) 
			{
				// its in between
				double mid = (item.decimal + nextItem.decimal)/2.0;
				if( actual.decimal < mid )
					return item ;
				else
					return nextItem;
			}
			ix++;
		}
			
		return null;
	}
	
	
	/**
	 * SCS uses an algorithm to calculate odds that is not exactly as advertised
	 * For example. 5-7 , is 0.71, which is closer to 0.5 than to 1.0 , so 1-2
	 * however, their simplified calculation method make this particular result come out to 1-1
	 * @param actual
	 * @return
	 */
	public Odds searchWithSCSTrueRounding( Odds actual ) {
		double div =  (double)Math.min( actual.fm, actual.to );
		int fm = (int)(0.5 + actual.fm / div );
		int to = (int)(0.5 + actual.to / div );
		
		if( fm == 1 && to > 2 )
			return new Odds( Special.BELOW );
		else
		if( to == 1 && fm > 4 )
			return new Odds( Special.OVER);
		
		return new Odds( fm,to) ;
	}
	
	
}

// final out come:
// header line :  Attacker Strength Down, 1,2,3,4,..... 
// dline1                              1, '1-1', '1-2', ...
